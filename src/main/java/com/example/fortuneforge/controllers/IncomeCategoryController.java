package com.example.fortuneforge.controllers;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.income.IncomeCategoryRequest;
import com.example.fortuneforge.services.income.IncomeCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("income/categories")
@RequiredArgsConstructor
public class IncomeCategoryController {

    private final IncomeCategoryService incomeCategoryService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> getAllCategories (@RequestBody IncomeCategoryRequest request) {

        return incomeCategoryService.getIncomeCategories(request);

    }


    @PostMapping("store")
    public ResponseEntity<ApiResponse> addIncomeCategory (@RequestBody @Valid IncomeCategoryRequest request) {

        return incomeCategoryService.addIncomeCategory(request);

    }

    @PostMapping("update/${categoryId}")
    public ResponseEntity<ApiResponse> updateIncomeCategory (@PathVariable Long categoryId, @RequestBody @Valid IncomeCategoryRequest request) {

        return incomeCategoryService.updateIncomeCategory(categoryId, request);

    }

}
