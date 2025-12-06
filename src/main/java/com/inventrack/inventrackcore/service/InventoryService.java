package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.InventoryDto;
import com.inventrack.inventrackcore.entity.Inventory;

import java.util.List;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto dto);

    InventoryDto updateInventory(Long id, InventoryDto dto);

    InventoryDto getInventoryById(Long id);

    InventoryDto getInventoryByProduct(Long productId);

    List<InventoryDto> getAllInventory();

    void deleteInventory(Long id);

    void increaseStock(Long productId, int qty);

    void decreaseStock(Long productId, int qty);

    Inventory getInventoryEntityByProduct(Long productId);
    Inventory saveInventoryEntity(Inventory inv);
}
