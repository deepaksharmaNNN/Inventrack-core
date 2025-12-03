package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.InventoryDto;
import com.inventrack.inventrackcore.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryDto toDto(Inventory inv) {
        if (inv == null) return null;

        boolean isBelow = inv.getCurrentStock() != null &&
                inv.getMinRequiredStock() != null &&
                inv.getCurrentStock() < inv.getMinRequiredStock();

        return InventoryDto.builder()
                .id(inv.getId())
                .productId(inv.getProduct().getId())
                .productName(inv.getProduct().getName())
                .currentStock(inv.getCurrentStock())
                .minRequiredStock(inv.getMinRequiredStock())
                .reorderLevel(inv.getReorderLevel())
                .autoReorder(inv.isAutoReorder())
                .belowThreshold(isBelow)
                .build();
    }

    public Inventory toEntity(InventoryDto dto) {
        Inventory inv = new Inventory();
        inv.setId(dto.getId());
        inv.setCurrentStock(dto.getCurrentStock());
        inv.setMinRequiredStock(dto.getMinRequiredStock());
        inv.setReorderLevel(dto.getReorderLevel());
        inv.setAutoReorder(dto.isAutoReorder());
        return inv;
    }
}
