package com.mizaniyati.service.interfaces;

import com.mizaniyati.dto.BudgetRequestDTO;
import com.mizaniyati.dto.BudgetResponseDTO;

import java.util.List;

public interface BudgetService {
    List<BudgetResponseDTO> getAllBudgets(String userEmail);
    BudgetResponseDTO createBudget(BudgetRequestDTO dto, String userEmail);
    BudgetResponseDTO updateBudget(Long id, BudgetRequestDTO dto, String userEmail);
    void deleteBudget(Long id, String userEmail);
}

