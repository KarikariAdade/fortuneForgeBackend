package com.example.fortuneforge.controllers.income;

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
    public ResponseEntity<ApiResponse> getAllCategories (@RequestHeader("Authorization") String token, @RequestBody IncomeCategoryRequest request) {

//        return ResponseEntity.ok(new ApiResponse("User categories retrieved successfully", token, null));
//
        return incomeCategoryService.getIncomeCategories(token);

    }


    @PostMapping("store")
    public ResponseEntity<ApiResponse> addIncomeCategory (@RequestHeader("Authorization") String token, @RequestBody @Valid IncomeCategoryRequest request) {

        return incomeCategoryService.addIncomeCategory(token, request);

    }

    @PostMapping("update/{categoryId}")
    public ResponseEntity<ApiResponse> updateIncomeCategory (@PathVariable Long categoryId, @RequestBody @Valid IncomeCategoryRequest request) {

        return incomeCategoryService.updateIncomeCategory(categoryId, request);

    }

}
