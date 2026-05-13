package com.mizaniyati.service.interfaces;

import com.mizaniyati.dto.ExpenseDTO;
import com.mizaniyati.dto.ExpenseRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ExpenseService {
    List<ExpenseDTO> getExpensesByUser(Long userId) ;
    ExpenseDTO createExpense(ExpenseRequestDTO dto, Long userId);
    ExpenseDTO updateExpense(Long Id ,ExpenseRequestDTO  dto ,  Long userId);
    void deleteExpense(Long id);
}
