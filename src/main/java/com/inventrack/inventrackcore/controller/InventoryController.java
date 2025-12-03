package com.inventrack.inventrackcore.controller;

import com.inventrack.inventrackcore.dto.InventoryDto;
import com.inventrack.inventrackcore.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryDto> create(@Valid @RequestBody InventoryDto dto) {
        InventoryDto created = inventoryService.createInventory(dto);
        return ResponseEntity.created(URI.create("/api/inventory/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDto>> all() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDto> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProduct(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> update(@PathVariable Long id, @Valid @RequestBody InventoryDto dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
    }

    @PostMapping("/{productId}/increase/{qty}")
    public ResponseEntity<Void> increase(@PathVariable Long productId, @PathVariable int qty) {
        inventoryService.increaseStock(productId, qty);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/decrease/{qty}")
    public ResponseEntity<Void> decrease(@PathVariable Long productId, @PathVariable int qty) {
        inventoryService.decreaseStock(productId, qty);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}