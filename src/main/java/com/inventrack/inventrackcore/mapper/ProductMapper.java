package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.ProductDto;
import com.inventrack.inventrackcore.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .description(product.getDescription())
                .supplierId(product.getSupplier() != null ? product.getSupplier().getId() : null)
                .supplierName(product.getSupplier() != null ? product.getSupplier().getName() : null)
                .build();
    }

    public Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .category(productDto.getCategory())
                .quantity(productDto.getQuantity())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .build();
    }
}
