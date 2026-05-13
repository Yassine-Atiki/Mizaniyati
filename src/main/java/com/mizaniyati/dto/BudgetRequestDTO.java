package com.mizaniyati.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetRequestDTO {

    @NotNull(message = "Limit amount is required")
    @Positive(message = "Limit amount must be positive")
    private BigDecimal limitAmount;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private Long categoryId;

    // Cette méthode permet à Jackson de récupérer l'ID de la catégorie dans tous les formats envoyés par React
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
