package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.StockMovementDto;
import com.inventrack.inventrackcore.enums.StockMovementType;

import java.util.List;

public interface StockMovementService {
    StockMovementDto logMovement(Long productId, int qtyChange, String reason, StockMovementType type, Long userId);
    List<StockMovementDto> getAll();
    List<StockMovementDto> getByProduct(Long productId);
    List<StockMovementDto> getByUser(Long userId);
}
