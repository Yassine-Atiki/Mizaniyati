package com.mizaniyati.service.impl;

import com.mizaniyati.dto.CategoryDTO;
import com.mizaniyati.entity.Category;
import com.mizaniyati.exception.ResourceNotFoundException;
import com.mizaniyati.mapper.CategoryMapper;
import com.mizaniyati.repository.CategoryRepository;
import com.mizaniyati.repository.ExpenseRepository;
import com.mizaniyati.service.interfaces.CategoryService;
import com.mizaniyati.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;
    private final ExpenseRepository expenseRepository;

    @Override
    public List<CategoryDTO> getCategoriesByUser(Long userId) {

        return categoryRepository.findByCreatedById(userId)
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO dto, Long userId) {
        Category category = categoryMapper.toEntity(dto);
        category.setCreatedBy(userService.findById(userId));
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto , Long userId) {
        Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Category updated = categoryMapper.toEntity(dto);
        category.setName(updated.getName());
        category.setIcon(updated.getIcon());
        category.setColorCode(updated.getColorCode());
        category.setCreatedBy(userService.findById(userId));

        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // interdit si utilisée par une dépense
        if (expenseRepository.existsByCategoryId(id)) {
            throw new RuntimeException("Impossible de supprimer : catégorie utilisée par une dépense");
        }

        categoryRepository.delete(category);
    }
}
