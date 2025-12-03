package com.inventrack.inventrackcore.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductDto {
    Long id;
    String name;
    String category;
    Integer quantity;
    Double price;
    String description;
    Long supplierId;
    String supplierName;
    Long categoryId;
    String categoryName;

}
