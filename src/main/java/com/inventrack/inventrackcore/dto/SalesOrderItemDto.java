package com.inventrack.inventrackcore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrderItemDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double subTotal;
}

