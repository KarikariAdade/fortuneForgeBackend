package com.example.fortuneforge.controllers.expense;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.expense.ExpenseRequest;
import com.example.fortuneforge.service_impl.expense.ExpenseServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("expense")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Expenses", description = "Expense Management")
public class ExpenseController {

    private final ExpenseServiceImpl expenseService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> listExpenses(@RequestHeader("Authorization") String token) {
        return expenseService.listExpenses(token);
    }

    @PostMapping("/store")
    public ResponseEntity<ApiResponse> storeExpense(@RequestHeader("Authorization") String token, @RequestBody @Valid ExpenseRequest request) {
        return expenseService.storeExpense(token, request);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateExpense(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid ExpenseRequest request) {
        return expenseService.updateExpense(token, id, request);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteExpense(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return expenseService.deleteExpense(token, id);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse> getExpense(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return expenseService.getExpenseDetails(token, id);
    }

}
