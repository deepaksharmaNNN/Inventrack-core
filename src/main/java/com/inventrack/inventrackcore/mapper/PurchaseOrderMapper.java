package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.PurchaseOrderDto;
import com.inventrack.inventrackcore.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PurchaseOrderMapper {

    private final PurchaseOrderItemMapper itemMapper;

    public PurchaseOrderMapper(PurchaseOrderItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public PurchaseOrderDto toDto(PurchaseOrder p) {
        return PurchaseOrderDto.builder()
                .id(p.getId())
                .supplierId(p.getSupplier().getId())
                .supplierName(p.getSupplier().getName())
                .orderDate(p.getOrderDate())
                .expectedDeliveryDate(p.getExpectedDeliveryDate())
                .status(p.getStatus())
                .totalAmount(p.getTotalAmount())
                .items(p.getItems().stream()
                        .map(itemMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}