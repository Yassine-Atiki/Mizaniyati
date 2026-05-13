package com.mizaniyati.controller;

import com.mizaniyati.dto.ExpenseDTO;
import com.mizaniyati.dto.ExpenseRequestDTO;
import com.mizaniyati.entity.User;
import com.mizaniyati.service.interfaces.ExpenseService;
import com.mizaniyati.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(expenseService.getExpensesByUser(user.getId()));
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseRequestDTO dto, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.createExpense(dto, user.getId()));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO>  updateExpense(@PathVariable Long id, @RequestBody ExpenseRequestDTO  dto, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(expenseService.updateExpense(id , dto, user.getId()));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void>  deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();

    }
}
