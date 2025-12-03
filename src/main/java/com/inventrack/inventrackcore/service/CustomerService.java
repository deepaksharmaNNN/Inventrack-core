package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.CustomerDto;
import com.inventrack.inventrackcore.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto dto);

    CustomerDto getCustomerById(Long id);

    List<CustomerDto> getAllCustomers();

    void deleteCustomer(Long id);

    Customer getCustomerEntity(Long id);
}
