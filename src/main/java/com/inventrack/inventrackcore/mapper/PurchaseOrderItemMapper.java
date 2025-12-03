package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.PurchaseOrderItemDto;
import com.inventrack.inventrackcore.entity.PurchaseOrderItem;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderItemMapper {

    public PurchaseOrderItemDto toDto(PurchaseOrderItem i) {
        return PurchaseOrderItemDto.builder()
                .productId(i.getProduct().getId())
                .productName(i.getProduct().getName())
                .quantity(i.getQuantity())
                .price(i.getPrice())
                .subTotal(i.getSubTotal())
                .build();
    }
}
