package com.inventrack.inventrackcore.service;


import com.inventrack.inventrackcore.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> searchByName(String name);
    void deleteProduct(Long id);
}
