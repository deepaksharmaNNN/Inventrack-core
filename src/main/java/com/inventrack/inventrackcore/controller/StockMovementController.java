package com.inventrack.inventrackcore.controller;

import com.inventrack.inventrackcore.dto.StockMovementDto;
import com.inventrack.inventrackcore.enums.StockMovementType;
import com.inventrack.inventrackcore.service.StockMovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@CrossOrigin
public class StockMovementController {

    private final StockMovementService service;

    public StockMovementController(StockMovementService service) {
        this.service = service;
    }

    @PostMapping("/log")
    public ResponseEntity<StockMovementDto> log(@RequestParam Long productId,
                                                @RequestParam int qtyChange,
                                                @RequestParam StockMovementType type,
                                                @RequestParam(required = false) String reason,
                                                @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(service.logMovement(productId, qtyChange, reason, type, userId));
    }

    @GetMapping
    public ResponseEntity<List<StockMovementDto>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovementDto>> byProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getByProduct(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StockMovementDto>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }
}
