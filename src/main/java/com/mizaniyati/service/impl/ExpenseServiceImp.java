package com.mizaniyati.service.impl;

import com.mizaniyati.dto.ExpenseDTO;
import com.mizaniyati.dto.ExpenseRequestDTO;
import com.mizaniyati.entity.Category;
import com.mizaniyati.entity.Expense;
import com.mizaniyati.exception.ResourceNotFoundException;
import com.mizaniyati.mapper.ExpenseMapper;
import com.mizaniyati.repository.CategoryRepository;
import com.mizaniyati.repository.ExpenseRepository;
import com.mizaniyati.service.interfaces.ExpenseService;
import com.mizaniyati.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@AllArgsConstructor
public class ExpenseServiceImp implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    public List<ExpenseDTO> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(ExpenseMapper::toDTO)
                .toList();
    }

    @Override
    public ExpenseDTO createExpense(ExpenseRequestDTO dto, Long userId) {
        if (dto.getCategoryId() == null) {
            throw new ResourceNotFoundException("Category is required");
        }
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Expense expense = ExpenseMapper.fromRequest(dto, category); // ← une ligne
        expense.setUser(userService.findById(userId));
        return ExpenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public ExpenseDTO updateExpense(Long id, ExpenseRequestDTO dto, Long userId) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        if (dto.getCategoryId() == null) {
            throw new ResourceNotFoundException("Category is required");
        }
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // met à jour les champs
        ExpenseMapper.updateFromRequest(expense, dto, category); // ← une ligne
        expense.setUser(userService.findById(userId));
        return ExpenseMapper.toDTO(expenseRepository.save(expense));
    }
    @Override
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);
    }
}
