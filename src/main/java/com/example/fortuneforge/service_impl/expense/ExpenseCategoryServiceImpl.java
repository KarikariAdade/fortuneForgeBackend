package com.example.fortuneforge.service_impl.expense;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.expense.ExpenseCategoryRequest;
import com.example.fortuneforge.services.expense.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {
    @Override
    public ResponseEntity<ApiResponse> listExpenseCategories(String token) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> storeExpenseCategory(String token, ExpenseCategoryRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateExpenseCategory(String token, Long id, ExpenseCategoryRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteExpenseCategory(String token, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getExpenseCategoryDetails(String token, Long id) {
        return null;
    }
}
