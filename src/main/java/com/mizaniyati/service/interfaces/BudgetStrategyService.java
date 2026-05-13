package com.mizaniyati.service.interfaces;

import com.mizaniyati.dto.BudgetStrategyRequestDTO;
import com.mizaniyati.dto.BudgetStrategyResponseDTO;

import java.util.List;

public interface BudgetStrategyService {
    List<BudgetStrategyResponseDTO> getAllStrategies(String userEmail);
    BudgetStrategyResponseDTO createStrategy(BudgetStrategyRequestDTO dto, String userEmail);
    BudgetStrategyResponseDTO updateStrategy(Long id, BudgetStrategyRequestDTO dto, String userEmail);
    void deleteStrategy(Long id, String userEmail);
    BudgetStrategyResponseDTO setActiveStrategy(Long id, String userEmail);
    BudgetStrategyResponseDTO deactivateStrategy(Long id, String userEmail);
}