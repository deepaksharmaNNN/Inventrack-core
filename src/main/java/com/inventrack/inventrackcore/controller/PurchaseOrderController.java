package com.inventrack.inventrackcore.controller;

import com.inventrack.inventrackcore.dto.PurchaseOrderDto;
import com.inventrack.inventrackcore.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    public PurchaseOrderController(PurchaseOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDto> create(@RequestBody PurchaseOrderDto dto) {
        return ResponseEntity.ok(service.createOrder(dto));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDto>> all() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderDto> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    @PostMapping("/{id}/receive")
    public ResponseEntity<PurchaseOrderDto> receive(@PathVariable Long id) {
        return ResponseEntity.ok(service.receiveOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
