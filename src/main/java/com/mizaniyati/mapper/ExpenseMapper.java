package com.mizaniyati.mapper;

import com.mizaniyati.dto.CategoryDTO;
import com.mizaniyati.dto.ExpenseDTO;
import com.mizaniyati.dto.ExpenseRequestDTO;
import com.mizaniyati.entity.Category;
import com.mizaniyati.entity.Expense;

public class ExpenseMapper {

    public static ExpenseDTO toDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setDescription(expense.getDescription());
        dto.setType(expense.getType());
        dto.setFrequency(expense.getFrequency());

        if (expense.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(expense.getCategory().getId());
            categoryDTO.setName(expense.getCategory().getName());
            categoryDTO.setIcon(expense.getCategory().getIcon());
            categoryDTO.setColorCode(expense.getCategory().getColorCode());
            dto.setCategory(categoryDTO);
        }

        return dto;
    }

    public static Expense toEntity(ExpenseDTO dto) {
        Expense expense = new Expense();
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setDescription(dto.getDescription());
        expense.setType(dto.getType());
        expense.setFrequency(dto.getFrequency());

        if (expense.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(expense.getCategory().getId());
            categoryDTO.setName(expense.getCategory().getName());
            categoryDTO.setIcon(expense.getCategory().getIcon());
            categoryDTO.setColorCode(expense.getCategory().getColorCode());
            dto.setCategory(categoryDTO);
        }

        return expense;
    }
    public static Expense fromRequest(ExpenseRequestDTO dto, Category category) {
        Expense expense = new Expense();
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setDescription(dto.getDescription());
        expense.setType(dto.getType());
        expense.setFrequency(dto.getFrequency());
        expense.setCategory(category);
        return expense;
    }
    public static void updateFromRequest(Expense expense, ExpenseRequestDTO dto, Category category) {
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setDescription(dto.getDescription());
        expense.setType(dto.getType());
        expense.setFrequency(dto.getFrequency());
        expense.setCategory(category);
    }
}