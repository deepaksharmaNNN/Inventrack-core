package com.inventrack.inventrackcore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItemDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double subTotal;
}
