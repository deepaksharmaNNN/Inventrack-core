package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.SalesOrderDto;
import com.inventrack.inventrackcore.dto.SalesOrderItemDto;
import com.inventrack.inventrackcore.entity.Customer;
import com.inventrack.inventrackcore.entity.Product;
import com.inventrack.inventrackcore.entity.SalesOrder;
import com.inventrack.inventrackcore.entity.SalesOrderItem;
import com.inventrack.inventrackcore.enums.SalesOrderStatus;
import com.inventrack.inventrackcore.exception.ResourceNotFoundException;
import com.inventrack.inventrackcore.mapper.SalesOrderMapper;
import com.inventrack.inventrackcore.repository.SalesOrderItemRepository;
import com.inventrack.inventrackcore.repository.SalesOrderRepository;
import com.inventrack.inventrackcore.service.CustomerService;
import com.inventrack.inventrackcore.service.InventoryService;
import com.inventrack.inventrackcore.service.ProductService;
import com.inventrack.inventrackcore.service.SalesOrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository orderRepo;
    private final SalesOrderItemRepository itemRepo;
    private final CustomerService customerService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final SalesOrderMapper mapper;

    public SalesOrderServiceImpl(SalesOrderRepository orderRepo,
                                 SalesOrderItemRepository itemRepo,
                                 CustomerService customerService,
                                 ProductService productService,
                                 InventoryService inventoryService,
                                 SalesOrderMapper mapper) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.customerService = customerService;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.mapper = mapper;
    }

    @Override
    public SalesOrderDto createOrder(SalesOrderDto dto) {

        Customer customer = customerService.getCustomerEntity(dto.getCustomerId());

        SalesOrder order = new SalesOrder();
        order.setCustomer(customer);
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(SalesOrderStatus.CREATED);

        List<SalesOrderItem> items = new ArrayList<>();
        double total = 0;

        for (SalesOrderItemDto i : dto.getItems()) {
            Product product = productService.getProductEntity(i.getProductId());

            SalesOrderItem item = SalesOrderItem.builder()
                    .salesOrder(order)
                    .product(product)
                    .price(i.getPrice())
                    .quantity(i.getQuantity())
                    .subTotal(i.getPrice() * i.getQuantity())
                    .build();

            total += item.getSubTotal();
            items.add(item);
        }

        order.setTotalAmount(total);
        order.setItems(items);

        SalesOrder saved = orderRepo.save(order);
        itemRepo.saveAll(items);

        return mapper.toDto(saved);
    }

    @Override
    public SalesOrderDto getOrderById(Long id) {
        return orderRepo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found: " + id));
    }

    @Override
    public List<SalesOrderDto> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public SalesOrderDto updateStatus(Long id, String status) {
        SalesOrder order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found: " + id));

        order.setStatus(SalesOrderStatus.valueOf(status));
        return mapper.toDto(orderRepo.save(order));
    }

    @Override
    public SalesOrderDto completeOrder(Long id) {
        SalesOrder order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found: " + id));

        // Deduct inventory
        for (SalesOrderItem item : order.getItems()) {
            inventoryService.decreaseStock(item.getProduct().getId(), item.getQuantity());
        }

        order.setStatus(SalesOrderStatus.COMPLETED);
        order.setCompletionDate(LocalDate.now());

        return mapper.toDto(orderRepo.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepo.existsById(id))
            throw new ResourceNotFoundException("Sales order not found: " + id);
        orderRepo.deleteById(id);
    }
}
