package com.mizaniyati.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "budget_strategies")
public class BudgetStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private BigDecimal savingPercentage;

    @NotNull
    @Column(nullable = false)
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private BigDecimal needsPercentage;

    @NotNull
    @Column(nullable = false)
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private BigDecimal wantsPercentage;

    @NotNull
    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
