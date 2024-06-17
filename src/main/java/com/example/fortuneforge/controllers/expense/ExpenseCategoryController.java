package com.example.fortuneforge.controllers.expense;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.expense.ExpenseCategoryRequest;
import com.example.fortuneforge.service_impl.expense.ExpenseCategoryServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("expense/category")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@Tag(name = "Expense Categories", description = "Expense Management")
public class ExpenseCategoryController {

    private final ExpenseCategoryServiceImpl expenseCategoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getExpenseCategory(@RequestHeader("Authorization") String token) {
        return expenseCategoryService.listExpenseCategories(token);
    }

    @PostMapping("store")
    public ResponseEntity<ApiResponse> storeExpenseCategory(@RequestHeader("Authorization") String token, @RequestBody @Valid ExpenseCategoryRequest request) {
        return expenseCategoryService.storeExpenseCategory(token, request);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateExpenseCategory(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid ExpenseCategoryRequest request) {
        return expenseCategoryService.updateExpenseCategory(token, id, request);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteExpenseCategory(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return expenseCategoryService.deleteExpenseCategory(token, id);
    }

    @GetMapping("details/{id}")
    public ResponseEntity<ApiResponse> getExpenseCategory(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return expenseCategoryService.getExpenseCategoryDetails(token, id);
    }
}
