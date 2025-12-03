package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.CategoryDto;
import com.inventrack.inventrackcore.entity.Category;
import com.inventrack.inventrackcore.exception.ResourceConflictException;
import com.inventrack.inventrackcore.exception.ResourceNotFoundException;
import com.inventrack.inventrackcore.mapper.CategoryMapper;
import com.inventrack.inventrackcore.repository.CategoryRepository;
import com.inventrack.inventrackcore.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;


    @Override
    public CategoryDto createCategory(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new ResourceConflictException("Category with name already exists: " + dto.getName());
        }

        Category c = mapper.toEntity(dto);
        return mapper.toDto(categoryRepository.save(c));
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));

        // Check for unique name
        if (!existing.getName().equals(dto.getName()) &&
                categoryRepository.existsByName(dto.getName())) {
            throw new ResourceConflictException("Another category already uses name: " + dto.getName());
        }

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());

        return mapper.toDto(categoryRepository.save(existing));
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }
}