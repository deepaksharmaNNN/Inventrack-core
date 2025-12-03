package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.SalesOrderDto;

import java.util.List;

public interface SalesOrderService {
    SalesOrderDto createOrder(SalesOrderDto dto);

    SalesOrderDto getOrderById(Long id);

    List<SalesOrderDto> getAllOrders();

    SalesOrderDto updateStatus(Long id, String status);

    SalesOrderDto completeOrder(Long id);

    void deleteOrder(Long id);
}
