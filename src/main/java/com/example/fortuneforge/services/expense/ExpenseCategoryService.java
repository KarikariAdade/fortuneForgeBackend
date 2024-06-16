package com.example.fortuneforge.services.expense;


import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.expense.ExpenseCategoryRequest;
import org.springframework.http.ResponseEntity;

public interface ExpenseCategoryService {

    ResponseEntity<ApiResponse> listExpenseCategories(String token);

    ResponseEntity<ApiResponse> storeExpenseCategory(String token, ExpenseCategoryRequest request);

    ResponseEntity<ApiResponse> updateExpenseCategory(String token, Long id, ExpenseCategoryRequest request);

    ResponseEntity<ApiResponse> deleteExpenseCategory(String token, Long id);

    ResponseEntity<ApiResponse> getExpenseCategoryDetails(String token, Long id);

}
