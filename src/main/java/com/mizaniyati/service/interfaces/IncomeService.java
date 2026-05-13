package com.mizaniyati.service.interfaces;

import com.mizaniyati.dto.IncomeRequestDTO;
import com.mizaniyati.dto.IncomeResponseDTO;

import java.util.List;

public interface IncomeService {
    List<IncomeResponseDTO> getAllIncomes(String userEmail);
    IncomeResponseDTO createIncome(IncomeRequestDTO dto, String userEmail);
    IncomeResponseDTO updateIncome(Long id, IncomeRequestDTO dto, String userEmail);
    void deleteIncome(Long id, String userEmail);
}

