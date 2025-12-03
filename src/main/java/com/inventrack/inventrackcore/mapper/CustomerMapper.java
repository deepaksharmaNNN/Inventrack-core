package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.CustomerDto;
import com.inventrack.inventrackcore.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto toDto(Customer c) {
        return CustomerDto.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .phone(c.getPhone())
                .address(c.getAddress())
                .build();
    }

    public Customer toEntity(CustomerDto dto) {
        return Customer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
    }
}
