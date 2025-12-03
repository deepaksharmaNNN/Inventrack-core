package com.inventrack.inventrackcore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDto {
    private Long id;

    private Long productId;
    private String productName;

    private Integer currentStock;
    private Integer minRequiredStock;
    private Integer reorderLevel;

    private boolean autoReorder;

    private boolean belowThreshold;
}
