package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.SalesOrderItemDto;
import com.inventrack.inventrackcore.entity.SalesOrderItem;
import org.springframework.stereotype.Component;

@Component
public class SalesOrderItemMapper {

    public SalesOrderItemDto toDto(SalesOrderItem s) {
        return SalesOrderItemDto.builder()
                .productId(s.getProduct().getId())
                .productName(s.getProduct().getName())
                .quantity(s.getQuantity())
                .price(s.getPrice())
                .subTotal(s.getSubTotal())
                .build();
    }
}