package com.example.fortuneforge.requests.expense;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ExpenseCategoryRequest {

    @NotEmpty(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    @NotNull(message = "Name field is required")
    private String name;

    private String userId;

    @Min(1)
    private double limit;

    private String description;

}
