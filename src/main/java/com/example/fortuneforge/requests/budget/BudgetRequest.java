package com.example.fortuneforge.requests.budget;

import com.example.fortuneforge.filters.BudgetAllocationFilter;
import com.example.fortuneforge.filters.BudgetIncomeFilter;
import com.example.fortuneforge.models.ExpenseCategory;
import com.example.fortuneforge.models.Income;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BudgetRequest {

    private Long id;

    @NotEmpty(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    @NotNull(message = "Name field is required")
    private String name;

    @NotEmpty(message = "End Date field is required")
    @NotBlank(message = "End Date field is required")
    @NotNull(message = "End Date field is required")
    private String endDate;

    @NotEmpty(message = "Start Date field is required")
    @NotBlank(message = "Start Date field is required")
    @NotNull(message = "Start Date field is required")
    private String startDate;

    private boolean hasEnded;

    @NotEmpty(message = "Income List field is required")
    @NotNull(message = "Income List field is required")
    private List<BudgetIncomeFilter> incomeList;

    @NotEmpty(message = "Expense Categories field is required")
    @NotNull(message = "Expense Categories field is required")
    private List<BudgetAllocationFilter> expenseCategories;
}
