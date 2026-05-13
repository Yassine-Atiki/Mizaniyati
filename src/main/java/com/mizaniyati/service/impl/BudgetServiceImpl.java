package com.mizaniyati.service.impl;

import com.mizaniyati.dto.BudgetRequestDTO;
import com.mizaniyati.dto.BudgetResponseDTO;
import com.mizaniyati.entity.Budget;
import com.mizaniyati.entity.Category;
import com.mizaniyati.entity.User;
import com.mizaniyati.mapper.BudgetMapper;
import com.mizaniyati.repository.BudgetRepository;
import com.mizaniyati.repository.CategoryRepository;
import com.mizaniyati.repository.ExpenseRepository;
import com.mizaniyati.service.interfaces.BudgetService;
import com.mizaniyati.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final BudgetMapper budgetMapper;

    @Override
    public List<BudgetResponseDTO> getAllBudgets(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return budgetRepository.findByUserId(user.getId())
                .stream()
                .map(budget -> enrichWithSpentAmount(budget, user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public BudgetResponseDTO createBudget(BudgetRequestDTO dto, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Budget budget = budgetMapper.toEntity(dto);
        budget.setUser(user);
        budget.setSpentAmount(BigDecimal.ZERO); // Initialisation

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            budget.setCategory(category);
        }

        Budget savedBudget = budgetRepository.save(budget);
        return enrichWithSpentAmount(savedBudget, user.getId());
    }

    @Override
    public BudgetResponseDTO updateBudget(Long id, BudgetRequestDTO dto, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Budget existingBudget = budgetRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Budget not found or you don't have permission"));

        budgetMapper.updateEntityFromDto(dto, existingBudget);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingBudget.setCategory(category);
        } else {
            existingBudget.setCategory(null);
        }

        Budget updatedBudget = budgetRepository.save(existingBudget);
        return enrichWithSpentAmount(updatedBudget, user.getId());
    }

    @Override
    public void deleteBudget(Long id, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Budget budget = budgetRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Budget not found or you don't have permission"));
        budgetRepository.delete(budget);
    }

    private BudgetResponseDTO enrichWithSpentAmount(Budget budget, Long userId) {
        BudgetResponseDTO dto = budgetMapper.toResponseDto(budget);
        if (budget.getCategory() != null) {
            BigDecimal spent = expenseRepository.calculateSpentAmountByBudget(
                    userId,
                    budget.getCategory().getId(),
                    budget.getStartDate(),
                    budget.getEndDate()
            );
            dto.setSpentAmount(spent);
        } else {
            dto.setSpentAmount(BigDecimal.ZERO);
        }
        return dto;
    }
}
