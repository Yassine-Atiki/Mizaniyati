package com.mizaniyati.controller;

import com.mizaniyati.dto.CategoryDTO;
import com.mizaniyati.entity.Category;
import com.mizaniyati.entity.User;
import com.mizaniyati.service.impl.CategoryServiceImpl;
import com.mizaniyati.service.interfaces.CategoryService;
import com.mizaniyati.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(Authentication authentication){
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(categoryService.getCategoriesByUser(user.getId()));

    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(Authentication authentication, @RequestBody CategoryDTO categoryDTO){
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDTO, user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(Authentication authentication, @PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO, user.getId()));}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory( @PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
