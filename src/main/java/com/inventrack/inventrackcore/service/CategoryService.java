package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.CategoryDto;
import com.inventrack.inventrackcore.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto dto);

    CategoryDto updateCategory(Long id, CategoryDto dto);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    void deleteCategory(Long id);

    Category getCategoryEntity(Long id); // for ProductService usage
}
