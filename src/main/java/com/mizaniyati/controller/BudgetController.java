package com.mizaniyati.controller;

import com.mizaniyati.dto.BudgetRequestDTO;
import com.mizaniyati.dto.BudgetResponseDTO;
import com.mizaniyati.service.interfaces.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<BudgetResponseDTO>> getAllBudgets(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(budgetService.getAllBudgets(userEmail));
    }

    @PostMapping
    public ResponseEntity<BudgetResponseDTO> createBudget(
            @Valid @RequestBody BudgetRequestDTO request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetService.createBudget(request, userEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponseDTO> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequestDTO request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(budgetService.updateBudget(id, request, userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        budgetService.deleteBudget(id, userEmail);
        return ResponseEntity.noContent().build();
    }
}

