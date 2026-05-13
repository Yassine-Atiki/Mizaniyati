package com.mizaniyati.service.interfaces;

import com.mizaniyati.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getCategoriesByUser(Long userId);
    CategoryDTO createCategory(CategoryDTO dto, Long userId);
    CategoryDTO updateCategory(Long id, CategoryDTO dto, Long userId);
    void deleteCategory(Long id);
}
