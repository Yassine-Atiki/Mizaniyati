package com.mizaniyati.controller;

import com.mizaniyati.dto.BudgetStrategyRequestDTO;
import com.mizaniyati.dto.BudgetStrategyResponseDTO;
import com.mizaniyati.service.interfaces.BudgetStrategyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-strategies")
@RequiredArgsConstructor
public class BudgetStrategyController {

    private final BudgetStrategyService budgetStrategyService;

    @GetMapping
    public ResponseEntity<List<BudgetStrategyResponseDTO>> getAllStrategies(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(budgetStrategyService.getAllStrategies(userEmail));
    }

    @PostMapping
    public ResponseEntity<BudgetStrategyResponseDTO> createStrategy(
            @Valid @RequestBody BudgetStrategyRequestDTO request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetStrategyService.createStrategy(request, userEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetStrategyResponseDTO> updateStrategy(
            @PathVariable Long id,
            @Valid @RequestBody BudgetStrategyRequestDTO request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(budgetStrategyService.updateStrategy(id, request, userEmail));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<BudgetStrategyResponseDTO> setActiveStrategy(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(budgetStrategyService.setActiveStrategy(id, userEmail));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<BudgetStrategyResponseDTO> setInactiveStrategy(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(budgetStrategyService.deactivateStrategy(id, userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrategy(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        budgetStrategyService.deleteStrategy(id, userEmail);
        return ResponseEntity.noContent().build();
    }
}
