package com.mizaniyati.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetResponseDTO {
    private Long id;
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long categoryId;
    private CategoryDTO category; // AJOUTÉ pour le frontend React
}
