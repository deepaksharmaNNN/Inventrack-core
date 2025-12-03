package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.SalesOrderDto;
import com.inventrack.inventrackcore.entity.SalesOrder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SalesOrderMapper {

    private final SalesOrderItemMapper itemMapper;

    public SalesOrderMapper(SalesOrderItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public SalesOrderDto toDto(SalesOrder o) {
        return SalesOrderDto.builder()
                .id(o.getId())
                .customerId(o.getCustomer().getId())
                .customerName(o.getCustomer().getName())
                .orderDate(o.getOrderDate())
                .completionDate(o.getCompletionDate())
                .status(o.getStatus())
                .totalAmount(o.getTotalAmount())
                .items(o.getItems().stream()
                        .map(itemMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
