package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.CustomerDto;
import com.inventrack.inventrackcore.entity.Customer;
import com.inventrack.inventrackcore.exception.ResourceNotFoundException;
import com.inventrack.inventrackcore.mapper.CustomerMapper;
import com.inventrack.inventrackcore.repository.CustomerRepository;
import com.inventrack.inventrackcore.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepository repo, CustomerMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!repo.existsById(id))
            throw new ResourceNotFoundException("Customer not found: " + id);
        repo.deleteById(id);
    }

    @Override
    public Customer getCustomerEntity(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }
}