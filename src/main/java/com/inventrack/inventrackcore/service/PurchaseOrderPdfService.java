package com.inventrack.inventrackcore.service;

public interface PurchaseOrderPdfService {
    byte[] generatePdf(Long purchaseOrderId);
}
