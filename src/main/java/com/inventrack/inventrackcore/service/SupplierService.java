package com.inventrack.inventrackcore.service;


import com.inventrack.inventrackcore.dto.SupplierDto;
import com.inventrack.inventrackcore.entity.Supplier;

import java.util.List;

public interface SupplierService {
    SupplierDto createSupplier(SupplierDto supplierDto);
    SupplierDto updateSupplier(Long id, SupplierDto dto);
    SupplierDto getSupplierById(Long id);
    List<SupplierDto> getAllSuppliers();
    void deleteSupplier(Long id);
    Supplier getSupplierEntityById(Long id);
}
