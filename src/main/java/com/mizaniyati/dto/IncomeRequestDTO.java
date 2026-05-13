package com.mizaniyati.dto;

import com.mizaniyati.enums.Frequency;
import com.mizaniyati.enums.IncomeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IncomeRequestDTO {

    @NotBlank(message = "Source is required")
    private String source;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private IncomeType type;
    private Frequency frequency;
    private Boolean recurring;
    private Long categoryId;

    @com.fasterxml.jackson.annotation.JsonProperty("category")
    public void unpackCategory(Object categoryObj) {
        if (categoryObj instanceof Number) {
            this.categoryId = ((Number) categoryObj).longValue();
        } else if (categoryObj instanceof String) {
            try {
                this.categoryId = Long.parseLong((String) categoryObj);
            } catch (NumberFormatException ignored) {}
        } else if (categoryObj instanceof java.util.Map) {
            Object idObj = ((java.util.Map<?, ?>) categoryObj).get("id");
            if (idObj instanceof Number) {
                this.categoryId = ((Number) idObj).longValue();
            } else if (idObj instanceof String) {
                try {
                    this.categoryId = Long.parseLong((String) idObj);
                } catch (NumberFormatException ignored) {}
            }
        }
    }
}
