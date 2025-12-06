package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.InventoryDto;
import com.inventrack.inventrackcore.entity.Inventory;
import com.inventrack.inventrackcore.entity.Product;
import com.inventrack.inventrackcore.exception.ResourceConflictException;
import com.inventrack.inventrackcore.exception.ResourceNotFoundException;
import com.inventrack.inventrackcore.mapper.InventoryMapper;
import com.inventrack.inventrackcore.repository.InventoryRepository;
import com.inventrack.inventrackcore.service.InventoryService;
import com.inventrack.inventrackcore.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductService productService;
    private final InventoryMapper inventoryMapper;


    @Override
    public InventoryDto createInventory(InventoryDto dto) {

        Product product = productService.getProductEntity(dto.getProductId());

        if (inventoryRepository.existsByProduct(product)) {
            throw new ResourceConflictException("Inventory already exists for product ID: " + dto.getProductId());
        }

        Inventory inv = inventoryMapper.toEntity(dto);
        inv.setProduct(product);

        return inventoryMapper.toDto(inventoryRepository.save(inv));
    }

    @Override
    public InventoryDto updateInventory(Long id, InventoryDto dto) {
        Inventory inv = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));

        inv.setCurrentStock(dto.getCurrentStock());
        inv.setMinRequiredStock(dto.getMinRequiredStock());
        inv.setReorderLevel(dto.getReorderLevel());
        inv.setAutoReorder(dto.isAutoReorder());

        return inventoryMapper.toDto(inventoryRepository.save(inv));
    }

    @Override
    public InventoryDto getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .map(inventoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));
    }

    @Override
    public InventoryDto getInventoryByProduct(Long productId) {
        Product product = productService.getProductEntity(productId);

        return inventoryRepository.findByProduct(product)
                .map(inventoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product ID: " + productId));
    }

    @Override
    public List<InventoryDto> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(inventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id))
            throw new ResourceNotFoundException("Inventory not found: " + id);

        inventoryRepository.deleteById(id);
    }

    @Override
    public void increaseStock(Long productId, int qty) {
        Product p = productService.getProductEntity(productId);

        Inventory inv = inventoryRepository.findByProduct(p)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product"));

        inv.setCurrentStock(inv.getCurrentStock() + qty);
        inventoryRepository.save(inv);
    }

    @Override
    public void decreaseStock(Long productId, int qty) {
        Product p = productService.getProductEntity(productId);

        Inventory inv = inventoryRepository.findByProduct(p)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product"));

        if (inv.getCurrentStock() < qty)
            throw new ResourceConflictException("Not enough stock");

        inv.setCurrentStock(inv.getCurrentStock() - qty);
        inventoryRepository.save(inv);
    }
    @Override
    public Inventory getInventoryEntityByProduct(Long productId) {
        Product product = productService.getProductEntity(productId);
        return inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId));
    }

    @Override
    public Inventory saveInventoryEntity(Inventory inv) {
        return inventoryRepository.save(inv);
    }

}
