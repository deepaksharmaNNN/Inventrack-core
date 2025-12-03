package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.PurchaseOrderDto;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrderDto createOrder(PurchaseOrderDto dto);

    PurchaseOrderDto getOrderById(Long id);

    List<PurchaseOrderDto> getAllOrders();

    PurchaseOrderDto updateStatus(Long id, String status);

    void deleteOrder(Long id);

    // Optional: Receiving order updates inventory
    PurchaseOrderDto receiveOrder(Long id);
}
