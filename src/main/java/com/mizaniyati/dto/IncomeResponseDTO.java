package com.mizaniyati.dto;

import com.mizaniyati.enums.Frequency;
import com.mizaniyati.enums.IncomeType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IncomeResponseDTO {
    private Long id;
    private String source;
    private BigDecimal amount;
    private LocalDate date;
    private IncomeType type;
    private Frequency frequency;
    private Boolean recurring;
    private Long categoryId;
    private CategoryDTO category;
}
