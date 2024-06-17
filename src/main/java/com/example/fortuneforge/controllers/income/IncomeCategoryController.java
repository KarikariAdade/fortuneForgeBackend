package com.example.fortuneforge.controllers.income;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.income.IncomeCategoryRequest;
import com.example.fortuneforge.services.income.IncomeCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("income/categories")
@RequiredArgsConstructor
@Tag(name = "Income Category", description = "Income Category Management")
public class IncomeCategoryController {

    private final IncomeCategoryService incomeCategoryService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> getAllCategories(@RequestHeader("Authorization") String token, @RequestBody IncomeCategoryRequest request) {

        return incomeCategoryService.getIncomeCategories(token);

    }


    @PostMapping("store")
    public ResponseEntity<ApiResponse> addIncomeCategory(@RequestHeader("Authorization") String token, @RequestBody @Valid IncomeCategoryRequest request) {

        return incomeCategoryService.addIncomeCategory(token, request);

    }

    @PostMapping("update/{categoryId}")
    public ResponseEntity<ApiResponse> updateIncomeCategory(@PathVariable Long categoryId, @RequestBody @Valid IncomeCategoryRequest request) {

        return incomeCategoryService.updateIncomeCategory(categoryId, request);

    }

}
