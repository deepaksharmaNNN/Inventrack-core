package com.inventrack.inventrackcore.controller;

import com.inventrack.inventrackcore.service.PurchaseOrderPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase-orders/pdf")
public class PurchaseOrderPdfController {

    private final PurchaseOrderPdfService pdfService;

    public PurchaseOrderPdfController(PurchaseOrderPdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        byte[] pdf = pdfService.generatePdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=po-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
