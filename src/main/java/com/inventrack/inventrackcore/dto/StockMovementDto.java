package com.inventrack.inventrackcore.dto;

import com.inventrack.inventrackcore.enums.StockMovementType;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer previousStock;
    private Integer changedByQty;
    private Integer newStock;
    private StockMovementType type;
    private String reason;
    private Instant createdAt;
    private Long performedByUserId;
}
