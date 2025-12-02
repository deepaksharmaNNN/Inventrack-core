package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.ProductDto;
import com.inventrack.inventrackcore.entity.Product;
import com.inventrack.inventrackcore.mapper.ProductMapper;
import com.inventrack.inventrackcore.repository.ProductRepository;
import com.inventrack.inventrackcore.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Test
    void testGetProductById() {
        ProductRepository repo = mock(ProductRepository.class);
        ProductMapper mapper = new ProductMapper();

        ProductServiceImpl service = new ProductServiceImpl(repo, null);

        Product p = Product.builder()
                .id(1L)
                .name("Test")
                .category("General")
                .quantity(5)
                .price(100.0)
                .build();

        when(repo.findById(1L)).thenReturn(Optional.of(p));

        ProductDto dto = service.getProductById(1L);

        assertEquals("Test", dto.getName());
        assertEquals(100.0, dto.getPrice());
    }
}
