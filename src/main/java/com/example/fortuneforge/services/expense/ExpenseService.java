package com.example.fortuneforge.services.expense;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.expense.ExpenseRequest;
import org.springframework.http.ResponseEntity;

public interface ExpenseService {

    ResponseEntity<ApiResponse> listExpenses(String token);

    ResponseEntity<ApiResponse> storeExpense(String token, ExpenseRequest request);

    ResponseEntity<ApiResponse> updateExpense(String token, Long id, ExpenseRequest request);

    ResponseEntity<ApiResponse> deleteExpense(String token, Long id);

    ResponseEntity<ApiResponse> getExpenseDetails(String token, Long id);

}
