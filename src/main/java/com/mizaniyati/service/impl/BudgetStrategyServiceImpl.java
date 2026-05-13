package com.mizaniyati.service.impl;

import com.mizaniyati.dto.BudgetStrategyRequestDTO;
import com.mizaniyati.dto.BudgetStrategyResponseDTO;
import com.mizaniyati.entity.BudgetStrategy;
import com.mizaniyati.entity.User;
import com.mizaniyati.exception.ResourceNotFoundException;
import com.mizaniyati.mapper.BudgetStrategyMapper;
import com.mizaniyati.repository.BudgetStrategyRepository;
import com.mizaniyati.service.interfaces.BudgetStrategyService;
import com.mizaniyati.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetStrategyServiceImpl implements BudgetStrategyService {

    private final BudgetStrategyRepository budgetStrategyRepository;
    private final BudgetStrategyMapper budgetStrategyMapper;
    private final UserService userService;

    private void validatePercentages(BudgetStrategyRequestDTO dto) {
        BigDecimal total = dto.getSavingPercentage().add(dto.getNeedsPercentage()).add(dto.getWantsPercentage());
        if (total.compareTo(new BigDecimal("100")) != 0) {
            throw new IllegalArgumentException("The total percentages (Savings, Needs, Wants) must exactly equal 100%");
        }
    }

    @Override
    public List<BudgetStrategyResponseDTO> getAllStrategies(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return budgetStrategyRepository.findByUserId(user.getId()).stream()
                .map(budgetStrategyMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetStrategyResponseDTO createStrategy(BudgetStrategyRequestDTO dto, String userEmail) {
        validatePercentages(dto);
        User user = userService.findByEmail(userEmail);
        BudgetStrategy strategy = budgetStrategyMapper.toEntity(dto);
        strategy.setUser(user);

        // If it's the first strategy, make it active by default
        List<BudgetStrategy> existing = budgetStrategyRepository.findByUserId(user.getId());
        strategy.setActive(existing.isEmpty());

        BudgetStrategy saved = budgetStrategyRepository.save(strategy);
        return budgetStrategyMapper.toResponseDto(saved);
    }

    @Override
    public BudgetStrategyResponseDTO updateStrategy(Long id, BudgetStrategyRequestDTO dto, String userEmail) {
        validatePercentages(dto);
        User user = userService.findByEmail(userEmail);
        BudgetStrategy strategy = budgetStrategyRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetStrategy not found or you don't have permission"));

        budgetStrategyMapper.updateEntityFromDto(dto, strategy);
        BudgetStrategy saved = budgetStrategyRepository.save(strategy);
        return budgetStrategyMapper.toResponseDto(saved);
    }

    @Override
    public void deleteStrategy(Long id, String userEmail) {
        User user = userService.findByEmail(userEmail);
        BudgetStrategy strategy = budgetStrategyRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetStrategy not found or you don't have permission"));

        if (strategy.isActive()) {
            throw new IllegalStateException("Cannot delete an active budget strategy. Please set another strategy as active first.");
        }

        budgetStrategyRepository.delete(strategy);
    }

    @Override
    public BudgetStrategyResponseDTO setActiveStrategy(Long id, String userEmail) {
        User user = userService.findByEmail(userEmail);

        // Find all strategies for user
        List<BudgetStrategy> userStrategies = budgetStrategyRepository.findByUserId(user.getId());

        // Target strategy
        BudgetStrategy targetStrategy = userStrategies.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("BudgetStrategy not found or you don't have permission"));

        // Deactivate all
        for (BudgetStrategy s : userStrategies) {
            s.setActive(false);
        }

        // Activate target
        targetStrategy.setActive(true);

        budgetStrategyRepository.saveAll(userStrategies);

        return budgetStrategyMapper.toResponseDto(targetStrategy);
    }

    @Override
    public BudgetStrategyResponseDTO deactivateStrategy(Long id, String userEmail) {
        User user = userService.findByEmail(userEmail);
        BudgetStrategy targetStrategy = budgetStrategyRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetStrategy not found or you don't have permission"));

        targetStrategy.setActive(false);
        budgetStrategyRepository.save(targetStrategy);
        return budgetStrategyMapper.toResponseDto(targetStrategy);
    }
}