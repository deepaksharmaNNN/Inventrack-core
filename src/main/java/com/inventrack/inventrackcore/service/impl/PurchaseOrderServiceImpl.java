package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.PurchaseOrderDto;
import com.inventrack.inventrackcore.dto.PurchaseOrderItemDto;
import com.inventrack.inventrackcore.entity.Product;
import com.inventrack.inventrackcore.entity.PurchaseOrder;
import com.inventrack.inventrackcore.entity.PurchaseOrderItem;
import com.inventrack.inventrackcore.entity.Supplier;
import com.inventrack.inventrackcore.enums.PurchaseOrderStatus;
import com.inventrack.inventrackcore.exception.ResourceNotFoundException;
import com.inventrack.inventrackcore.mapper.PurchaseOrderMapper;
import com.inventrack.inventrackcore.repository.PurchaseOrderItemRepository;
import com.inventrack.inventrackcore.repository.PurchaseOrderRepository;
import com.inventrack.inventrackcore.service.InventoryService;
import com.inventrack.inventrackcore.service.ProductService;
import com.inventrack.inventrackcore.service.PurchaseOrderService;
import com.inventrack.inventrackcore.service.SupplierService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository orderRepo;
    private final PurchaseOrderItemRepository itemRepo;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final PurchaseOrderMapper mapper;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository orderRepo,
                                    PurchaseOrderItemRepository itemRepo,
                                    SupplierService supplierService,
                                    ProductService productService,
                                    InventoryService inventoryService,
                                    PurchaseOrderMapper mapper) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.supplierService = supplierService;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.mapper = mapper;
    }

    @Override
    public PurchaseOrderDto createOrder(PurchaseOrderDto dto) {

        Supplier supplier = supplierService.getSupplierEntityById(dto.getSupplierId());

        PurchaseOrder order = new PurchaseOrder();
        order.setSupplier(supplier);
        order.setOrderDate(dto.getOrderDate());
        order.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        order.setStatus(PurchaseOrderStatus.CREATED);

        List<PurchaseOrderItem> items = new ArrayList<>();
        double total = 0;

        for (PurchaseOrderItemDto i : dto.getItems()) {
            Product product = productService.getProductEntity(i.getProductId());

            PurchaseOrderItem item = PurchaseOrderItem.builder()
                    .purchaseOrder(order)
                    .product(product)
                    .quantity(i.getQuantity())
                    .price(i.getPrice())
                    .subTotal(i.getPrice() * i.getQuantity())
                    .build();

            total += item.getSubTotal();
            items.add(item);
        }

        order.setItems(items);
        order.setTotalAmount(total);

        PurchaseOrder saved = orderRepo.save(order);
        itemRepo.saveAll(items);

        return mapper.toDto(saved);
    }

    @Override
    public PurchaseOrderDto getOrderById(Long id) {
        return orderRepo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    @Override
    public List<PurchaseOrderDto> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public PurchaseOrderDto updateStatus(Long id, String status) {
        PurchaseOrder order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        order.setStatus(PurchaseOrderStatus.valueOf(status));

        return mapper.toDto(orderRepo.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepo.existsById(id))
            throw new ResourceNotFoundException("Order not found: " + id);
        orderRepo.deleteById(id);
    }

    @Override
    public PurchaseOrderDto receiveOrder(Long id) {
        PurchaseOrder order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        // Update inventory for each product
        for (PurchaseOrderItem item : order.getItems()) {
            inventoryService.increaseStock(item.getProduct().getId(), item.getQuantity());
        }

        order.setStatus(PurchaseOrderStatus.RECEIVED);

        return mapper.toDto(orderRepo.save(order));
    }
}