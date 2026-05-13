package com.mizaniyati.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetStrategyResponseDTO {
    private Long id;
    private String name;
    private BigDecimal savingPercentage;
    private BigDecimal needsPercentage;
    private BigDecimal wantsPercentage;
    private boolean isActive;
}

