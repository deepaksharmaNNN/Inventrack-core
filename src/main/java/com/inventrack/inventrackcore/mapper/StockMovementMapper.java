package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.StockMovementDto;
import com.inventrack.inventrackcore.entity.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovementDto toDto(StockMovement s) {
        if (s == null) return null;
        return StockMovementDto.builder()
                .id(s.getId())
                .productId(s.getProduct().getId())
                .productName(s.getProduct().getName())
                .previousStock(s.getPreviousStock())
                .changedByQty(s.getChangedByQty())
                .newStock(s.getNewStock())
                .type(s.getType())
                .reason(s.getReason())
                .createdAt(s.getCreatedAt())
                .performedByUserId(s.getPerformedByUserId())
                .build();
    }
}
