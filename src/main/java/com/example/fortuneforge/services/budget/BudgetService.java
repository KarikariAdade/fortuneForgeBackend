package com.example.fortuneforge.services.budget;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.budget.BudgetRequest;
import org.springframework.http.ResponseEntity;

public interface BudgetService {

    public ResponseEntity<ApiResponse> getBudgets(String token);

    public ResponseEntity<ApiResponse> storeBudget(String token, BudgetRequest request);

    public ResponseEntity<ApiResponse> updateBudget(String token, Long id, BudgetRequest request);

    public ResponseEntity<ApiResponse> deleteBudget(String token, Long id);

    public ResponseEntity<ApiResponse> getBudgetDetails(String token, Long id);
}
