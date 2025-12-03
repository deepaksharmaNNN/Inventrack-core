package com.inventrack.inventrackcore.controller;

import com.inventrack.inventrackcore.dto.SalesOrderDto;
import com.inventrack.inventrackcore.service.SalesOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-orders")
@CrossOrigin
public class SalesOrderController {

    private final SalesOrderService service;

    public SalesOrderController(SalesOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SalesOrderDto> create(@RequestBody SalesOrderDto dto) {
        return ResponseEntity.ok(service.createOrder(dto));
    }

    @GetMapping
    public ResponseEntity<List<SalesOrderDto>> getAll() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SalesOrderDto> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<SalesOrderDto> complete(@PathVariable Long id) {
        return ResponseEntity.ok(service.completeOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
