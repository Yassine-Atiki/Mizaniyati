package com.mizaniyati.dto;

import com.mizaniyati.enums.ExpenseType;
import com.mizaniyati.enums.Frequency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequestDTO {
    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private ExpenseType type;
    private Frequency frequency;
    private Long categoryId;  // React envoie juste l'id
}
