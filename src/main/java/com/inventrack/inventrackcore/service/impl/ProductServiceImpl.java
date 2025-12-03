package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.ProductDto;
import com.inventrack.inventrackcore.entity.Product;
import com.inventrack.inventrackcore.entity.Supplier;
import com.inventrack.inventrackcore.exception.ResourceNotFoundException;
import com.inventrack.inventrackcore.mapper.ProductMapper;
import com.inventrack.inventrackcore.repository.ProductRepository;
import com.inventrack.inventrackcore.service.ProductService;
import com.inventrack.inventrackcore.service.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final SupplierService supplierService;

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = mapper.toEntity(dto);

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierService.getSupplierEntityById(dto.getSupplierId());
            product.setSupplier(supplier);
        }

        return mapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        existing.setName(dto.getName());
        existing.setCategory(dto.getCategory());
        existing.setQuantity(dto.getQuantity());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierService.getSupplierEntityById(dto.getSupplierId());
            existing.setSupplier(supplier);
        } else {
            existing.setSupplier(null);
        }

        return mapper.toDto(productRepository.save(existing));
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product not found: " + id);
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductEntity(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
    }
}
