package com.mizaniyati.controller;

import com.mizaniyati.dto.IncomeRequestDTO;
import com.mizaniyati.dto.IncomeResponseDTO;
import com.mizaniyati.service.interfaces.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> getAllIncomes(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(incomeService.getAllIncomes(userEmail));
    }

    @PostMapping
    public ResponseEntity<IncomeResponseDTO> createIncome(
            @Valid @RequestBody IncomeRequestDTO request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(incomeService.createIncome(request, userEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> updateIncome(
            @PathVariable Long id,
            @Valid @RequestBody IncomeRequestDTO request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(incomeService.updateIncome(id, request, userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        incomeService.deleteIncome(id, userEmail);
        return ResponseEntity.noContent().build();
    }
}

