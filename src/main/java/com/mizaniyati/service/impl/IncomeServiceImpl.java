package com.mizaniyati.service.impl;

import com.mizaniyati.dto.IncomeRequestDTO;
import com.mizaniyati.dto.IncomeResponseDTO;
import com.mizaniyati.entity.Category;
import com.mizaniyati.entity.Income;
import com.mizaniyati.entity.User;
import com.mizaniyati.mapper.IncomeMapper;
import com.mizaniyati.repository.CategoryRepository;
import com.mizaniyati.repository.IncomeRepository;
import com.mizaniyati.service.interfaces.IncomeService;
import com.mizaniyati.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final IncomeMapper incomeMapper;

    @Override
    public List<IncomeResponseDTO> getAllIncomes(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return incomeRepository.findByUserId(user.getId())
                .stream()
                .map(incomeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public IncomeResponseDTO createIncome(IncomeRequestDTO dto, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Income income = incomeMapper.toEntity(dto);
        income.setUser(user);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            income.setCategory(category);
        }

        Income savedIncome = incomeRepository.save(income);
        return incomeMapper.toResponseDto(savedIncome);
    }

    @Override
    public IncomeResponseDTO updateIncome(Long id, IncomeRequestDTO dto, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Income existingIncome = incomeRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Income not found or you don't have permission"));

        incomeMapper.updateEntityFromDto(dto, existingIncome);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingIncome.setCategory(category);
        } else {
            existingIncome.setCategory(null);
        }

        Income updatedIncome = incomeRepository.save(existingIncome);
        return incomeMapper.toResponseDto(updatedIncome);
    }

    @Override
    public void deleteIncome(Long id, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Income income = incomeRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Income not found or you don't have permission"));
        incomeRepository.delete(income);
    }
}

