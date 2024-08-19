package com.example.fortuneforge.controllers.budget;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.budget.BudgetRequest;
import com.example.fortuneforge.service_impl.budget.BudgetServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("budget")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@Tag(name = "Budgets", description = "Budget Management")
public class BudgetController {

    private final BudgetServiceImpl budgetService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getBudgets(@RequestHeader("Authorization") String token) {
        return budgetService.getBudgets(token);
    }

    @PostMapping("/store")
    public ResponseEntity<ApiResponse> storeBudget(@RequestHeader("Authorization") String token, @RequestBody @Valid BudgetRequest request) {
        return budgetService.storeBudget(token, request);
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateBudget(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid BudgetRequest request) {
        return budgetService.updateBudget(token, id, request);
    }

    @GetMapping("/budget/{id}/details")
    public ResponseEntity<ApiResponse> getBudgetDetails(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return budgetService.getBudgetDetails(token, id);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBudget(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return budgetService.deleteBudget(token, id);
    }




}
