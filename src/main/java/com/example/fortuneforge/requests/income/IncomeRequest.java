package com.example.fortuneforge.requests.income;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeRequest {

    private Long id;

    @NotEmpty(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    @NotNull(message = "Name field is required")
    private String name;

    private String userId;

    @NotEmpty(message = "Category Field is required")
    @NotBlank(message = "Category Field is required")
    @NotNull(message = "Category Field is required")
    private String incomeCategory;

    @NotEmpty(message = "Amount Field is required")
    @NotBlank(message = "Amount Field is required")
    @NotNull(message = "Amount Field is required")
    private String amount;

    private String startDate;

    private String endDate;

    private boolean recurring;

    private String description;


}
