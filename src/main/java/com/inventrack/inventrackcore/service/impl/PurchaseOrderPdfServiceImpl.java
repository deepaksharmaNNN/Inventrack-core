package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.PurchaseOrderDto;
import com.inventrack.inventrackcore.dto.PurchaseOrderItemDto;
import com.inventrack.inventrackcore.service.PurchaseOrderPdfService;
import com.inventrack.inventrackcore.service.PurchaseOrderService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PurchaseOrderPdfServiceImpl implements PurchaseOrderPdfService {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderPdfServiceImpl(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public byte[] generatePdf(Long purchaseOrderId) {
        PurchaseOrderDto po = purchaseOrderService.getOrderById(purchaseOrderId);

        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            float margin = 50;
            float y = page.getMediaBox().getHeight() - margin;

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
            cs.newLineAtOffset(margin, y);
            cs.showText("Purchase Order");
            cs.endText();

            y -= 30;

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 12);
            cs.newLineAtOffset(margin, y);
            cs.showText("PO ID: " + po.getId());
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 12);
            cs.newLineAtOffset(margin + 200, y);
            cs.showText("Supplier: " + po.getSupplierName());
            cs.endText();

            y -= 20;
            DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 12);
            cs.newLineAtOffset(margin, y);
            cs.showText("Order Date: " + (po.getOrderDate() != null ? po.getOrderDate().format(fmt) : ""));
            cs.endText();

            y -= 25;

            // table header
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
            cs.newLineAtOffset(margin, y);
            cs.showText(String.format("%-40s %10s %12s %12s", "Product", "Qty", "Unit Price", "Subtotal"));
            cs.endText();

            y -= 15;

            cs.setFont(PDType1Font.HELVETICA, 11);
            for (PurchaseOrderItemDto item : po.getItems()) {
                if (y < 80) {
                    cs.close();
                    page = new PDPage(PDRectangle.A4);
                    doc.addPage(page);
                    cs = new PDPageContentStream(doc, page);
                    y = page.getMediaBox().getHeight() - margin;
                }

                cs.beginText();
                cs.newLineAtOffset(margin, y);
                String line = String.format("%-40s %10d %12.2f %12.2f",
                        trim(item.getProductName(), 35),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSubTotal());
                cs.showText(line);
                cs.endText();
                y -= 15;
            }

            y -= 15;
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
            cs.newLineAtOffset(margin, y);
            cs.showText("Total: " + (po.getTotalAmount() == null ? "0.00" : String.format("%.2f", po.getTotalAmount())));
            cs.endText();

            cs.close();

            doc.save(out);
            return out.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate PDF", ex);
        }
    }

    private String trim(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }
}
