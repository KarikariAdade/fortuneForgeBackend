package com.example.fortuneforge.service_impl.budget;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.config.CustomInternalServerErrorException;
import com.example.fortuneforge.filters.BudgetAllocationFilter;
import com.example.fortuneforge.filters.BudgetIncomeFilter;
import com.example.fortuneforge.models.*;
import com.example.fortuneforge.repositories.budget.BudgetAllocationRepository;
import com.example.fortuneforge.repositories.budget.BudgetIncomeRepository;
import com.example.fortuneforge.repositories.budget.BudgetRepository;
import com.example.fortuneforge.repositories.expense.ExpenseCategoryRepository;
import com.example.fortuneforge.repositories.income.IncomeRepository;
import com.example.fortuneforge.requests.budget.BudgetRequest;
import com.example.fortuneforge.services.AuthenticationService;
import com.example.fortuneforge.services.budget.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service

public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final AuthenticationService authenticationService;

    private final IncomeRepository incomeRepository;

    private final BudgetIncomeRepository budgetIncomeRepository;

    private final ExpenseCategoryRepository expenseCategoryRepository;

    private final BudgetAllocationRepository budgetAllocationRepository;

    @Override
    public ResponseEntity<ApiResponse> getBudgets(String token) {

        try {

            User user = authenticationService.validateUserFromToken(token);

            List<Budget> budgetList = budgetRepository.listBudgetByUserId(user.getId());

            return ResponseEntity.ok(new ApiResponse("Budgets retrieved successfully", budgetList, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Expense could not be retrieved", exception);

        }

    }


    @Transactional(rollbackFor = {Exception.class, CustomInternalServerErrorException.class})
    @Override
    public ResponseEntity<ApiResponse> storeBudget(String token, BudgetRequest request) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            List<BudgetAllocationFilter> budgetAllocationFilter = request.getExpenseCategories();


            List<BudgetIncomeFilter> incomeFilterList = request.getIncomeList();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Budget budget = saveBudget(request, user, formatter);

            List<BudgetIncome> budgetIncomeList = saveBudgetIncome(incomeFilterList, budget);

            List<BudgetAllocation> budgetAllocationList = saveBudgetAllocation(budgetAllocationFilter, budget);

            if (budgetAllocationList.isEmpty())
                throw new CustomInternalServerErrorException("At least one expense category is required");

            if (budgetIncomeList.isEmpty())
                throw new CustomInternalServerErrorException("At least one income is required");

            budgetAllocationRepository.saveAllAndFlush(budgetAllocationList);

            budgetIncomeRepository.saveAllAndFlush(budgetIncomeList);

            return ResponseEntity.ok(new ApiResponse("Budget created successfully", budget, null));

        } catch (CustomInternalServerErrorException exception) {

            return ResponseEntity.internalServerError().body(new ApiResponse(exception.getMessage(), null, null));

        }
        catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Budget could not be saved ", exception);
        }
    }



    @Override
    public ResponseEntity<ApiResponse> updateBudget(String token, Long id, BudgetRequest request) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            List<BudgetAllocationFilter> budgetAllocationFilter = request.getExpenseCategories();

            List<BudgetIncomeFilter> incomeFilterList = request.getIncomeList();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            HashMap<String, Object[]> allocationCollection = new HashMap<>();

            List<BudgetAllocation> checkExistingAllocation = budgetAllocationRepository.getAllocationWithSelectedBudget(id);

            System.out.println("Check existing allocation" + checkExistingAllocation.toString());

            Optional<Budget> budget = budgetRepository.findById(id);

            if (budget.isEmpty())
                return ResponseEntity.internalServerError().body(new ApiResponse("Selected budget not found", null, null));

            budgetAllocationFilter.forEach(budgetAllocation -> {

                if (!checkExistingAllocation.isEmpty()){
                    System.out.println("it is not empty");

                    BudgetAllocation newBudgetAllocation = new BudgetAllocation();

                    Optional<ExpenseCategory> category = expenseCategoryRepository.findById(budgetAllocation.getCategoryId());

                    checkExistingAllocation.forEach(existingAllocation -> {

                        if (existingAllocation.getExpenseCategory().getId() != budgetAllocation.getCategoryId()) {
                            budgetAllocationRepository.delete(existingAllocation);

                            // The else block here is unnecessary because we only create a new allocation if the IDs don't match
                            newBudgetAllocation.setExpenseCategory(category.get());
                            newBudgetAllocation.setBudget(budget.get());

                            budgetAllocationRepository.save(newBudgetAllocation);
                        }
                    });

                } else {
                    System.out.println("it is empty");

                    Optional<ExpenseCategory> category = expenseCategoryRepository.findById(budgetAllocation.getCategoryId());

                    if (category.isPresent()) {
                        System.out.println("category is present");
                        BudgetAllocation newBudgetAllocation = new BudgetAllocation();
                        newBudgetAllocation.setExpenseCategory(category.get());
                        newBudgetAllocation.setBudget(budget.get());

                        budgetAllocationRepository.save(newBudgetAllocation);
                    }
                }

            });


            return ResponseEntity.ok(new ApiResponse("hello world", null, null));


        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Budget could not be saved ", exception);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteBudget(String token, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getBudgetDetails(String token, Long id) {
        return null;
    }

    private List<BudgetAllocation> saveBudgetAllocation(List<BudgetAllocationFilter> budgetAllocationFilter, Budget budget) {

        List<BudgetAllocation> budgetAllocationList = new ArrayList<>();

        budgetAllocationFilter.forEach(budgetFilter -> {

            Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(budgetFilter.getCategoryId());

            if (expenseCategory.isPresent()) {

                BudgetAllocation budgetAllocation = new BudgetAllocation();

                budgetAllocation.setBudget(budget);

                budgetAllocation.setExpenseCategory(expenseCategory.get());

                budgetAllocationList.add(budgetAllocation);

            }

        });

        return budgetAllocationList;
    }

    private List<BudgetIncome> saveBudgetIncome(List<BudgetIncomeFilter> incomeFilterList, Budget budget) {

        List<BudgetIncome> budgetIncomeList = new ArrayList<>();

        incomeFilterList.forEach(budgetFilter -> {

            Optional<Income> income = incomeRepository.findById(budgetFilter.getIncomeId());

            if (income.isPresent()) {

                BudgetIncome budgetIncome = new BudgetIncome();

                budgetIncome.setBudget(budget);

                budgetIncome.setIncome(income.get());

                budgetIncomeList.add(budgetIncome);
            }

        });

        return budgetIncomeList;

    }

    private Budget saveBudget(BudgetRequest request, User user, DateTimeFormatter formatter) {
        Budget budget = new Budget();

        budget.setUser(user);

        budget.setName(request.getName());

        budget.setEndDate(LocalDate.parse(request.getEndDate(), formatter));

        budget.setStartDate(LocalDate.parse(request.getStartDate(), formatter));

        budget = budgetRepository.save(budget);

        return budget;
    }


}
