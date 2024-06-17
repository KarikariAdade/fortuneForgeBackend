package com.example.fortuneforge.requests.expense;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpenseRequest {

    @NotEmpty(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    @NotNull(message = "Name field is required")
    private String name;

    @Min(1)
    private double amount;

    private String description;

    @NotEmpty(message = "Expense Category field is required")
    @NotBlank(message = "Expense Category field is required")
    @NotNull(message = "Expense Category field is required")
    private String expenseCategory;

}
