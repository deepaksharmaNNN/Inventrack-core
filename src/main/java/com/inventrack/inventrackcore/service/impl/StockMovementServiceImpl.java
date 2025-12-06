package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.StockMovementDto;
import com.inventrack.inventrackcore.entity.Inventory;
import com.inventrack.inventrackcore.entity.Product;
import com.inventrack.inventrackcore.entity.StockMovement;
import com.inventrack.inventrackcore.enums.StockMovementType;
import com.inventrack.inventrackcore.mapper.StockMovementMapper;
import com.inventrack.inventrackcore.repository.StockMovementRepository;
import com.inventrack.inventrackcore.service.InventoryService;
import com.inventrack.inventrackcore.service.ProductService;
import com.inventrack.inventrackcore.service.StockMovementService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository repo;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final StockMovementMapper mapper;

    public StockMovementServiceImpl(StockMovementRepository repo,
                                    ProductService productService,
                                    InventoryService inventoryService,
                                    StockMovementMapper mapper) {
        this.repo = repo;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.mapper = mapper;
    }

    @Override
    public StockMovementDto logMovement(Long productId, int qtyChange, String reason, StockMovementType type, Long performedByUserId) {
        Product p = productService.getProductEntity(productId); // uses ProductService (not repo)
        Inventory inv = inventoryService.getInventoryEntityByProduct(productId); // we will add this helper (see note)

        int prev = inv.getCurrentStock() == null ? 0 : inv.getCurrentStock();
        int newStock = prev + qtyChange;

        // update inventory
        inv.setCurrentStock(newStock);
        // inventoryService.saveInventoryEntity(inv) — let's ensure inventoryService has method to save entity
        inventoryService.saveInventoryEntity(inv);

        StockMovement sm = StockMovement.builder()
                .product(p)
                .previousStock(prev)
                .changedByQty(qtyChange)
                .newStock(newStock)
                .type(type)
                .reason(reason)
                .createdAt(Instant.now())
                .performedByUserId(performedByUserId)
                .build();

        StockMovement saved = repo.save(sm);
        return mapper.toDto(saved);
    }

    @Override
    public List<StockMovementDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<StockMovementDto> getByProduct(Long productId) {
        Product p = productService.getProductEntity(productId);
        return repo.findByProduct(p).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<StockMovementDto> getByUser(Long userId) {
        return repo.findByPerformedByUserId(userId).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
