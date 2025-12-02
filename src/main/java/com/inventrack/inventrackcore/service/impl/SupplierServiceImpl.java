package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.SupplierDto;
import com.inventrack.inventrackcore.entity.Supplier;
import com.inventrack.inventrackcore.exception.ResourceConflictException;
import com.inventrack.inventrackcore.exception.ResourceNotFoundException;
import com.inventrack.inventrackcore.mapper.SupplierMapper;
import com.inventrack.inventrackcore.repository.SupplierRepository;
import com.inventrack.inventrackcore.service.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper mapper;

    @Override
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        if (supplierDto.getContactEmail() != null && supplierRepository.existsByContactEmail(supplierDto.getContactEmail())) {
            throw new ResourceConflictException("Supplier with email already exists: " + supplierDto.getContactEmail());
        }
        Supplier s = mapper.toEntity(supplierDto);
        Supplier saved = supplierRepository.save(s);
        return mapper.toDto(saved);
    }

    @Override
    public SupplierDto updateSupplier(Long id, SupplierDto dto) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));

        // if email changed ensure uniqueness
        if (dto.getContactEmail() != null && !dto.getContactEmail().equals(existing.getContactEmail())) {
            if (supplierRepository.existsByContactEmail(dto.getContactEmail())) {
                throw new ResourceConflictException("Another supplier already uses email: " + dto.getContactEmail());
            }
        }

        mapper.updateEntityFromDto(dto, existing);
        Supplier saved = supplierRepository.save(existing);
        return mapper.toDto(saved);
    }

    @Override
    public SupplierDto getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
