package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.SupplierDto;
import com.inventrack.inventrackcore.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierDto toDto(Supplier supplier){
        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .contactEmail(supplier.getContactEmail())
                .contactPhone(supplier.getContactPhone())
                .address(supplier.getAddress())
                .build();
    }
    public Supplier toEntity(SupplierDto supplierDto){
        return Supplier.builder()
                .id(supplierDto.getId())
                .name(supplierDto.getName())
                .contactEmail(supplierDto.getContactEmail())
                .contactPhone(supplierDto.getContactPhone())
                .address(supplierDto.getAddress())
                .build();
    }
    public void updateEntityFromDto(SupplierDto dto, Supplier entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setContactEmail(dto.getContactEmail());
        entity.setContactPhone(dto.getContactPhone());
        entity.setAddress(dto.getAddress());
    }
}
