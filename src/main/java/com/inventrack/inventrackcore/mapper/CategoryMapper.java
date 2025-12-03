package com.inventrack.inventrackcore.mapper;

import com.inventrack.inventrackcore.dto.CategoryDto;
import com.inventrack.inventrackcore.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category c) {
        if (c == null) return null;

        return CategoryDto.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .build();
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
