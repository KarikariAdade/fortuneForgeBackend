package com.example.fortuneforge.service_impl.expense;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.ExpenseCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.expense.ExpenseCategoryRepository;
import com.example.fortuneforge.requests.expense.ExpenseCategoryRequest;
import com.example.fortuneforge.services.AuthenticationService;
import com.example.fortuneforge.services.expense.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.thymeleaf.util.ListUtils.isEmpty;


@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final AuthenticationService authenticationService;

    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public ResponseEntity<ApiResponse> listExpenseCategories(String token) {
        try {
            User user = authenticationService.validateUserFromToken(token);

            List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAllByUserId(user.getId());

            return ResponseEntity.ok(new ApiResponse("Expense Category retrieved", !isEmpty(expenseCategoryList) ? expenseCategoryList : null, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Expense category could not be created", exception);

        }
    }

    @Override
    public ResponseEntity<ApiResponse> storeExpenseCategory(String token, ExpenseCategoryRequest request) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            ExpenseCategory expenseCategory = new ExpenseCategory();

            dumpExpenseCategory(request, expenseCategory, user);

            return ResponseEntity.ok(new ApiResponse("Expense category created successfully", expenseCategory, null));

        } catch (Exception exception) {
            return CatchErrorResponses.catchErrors("Goal category update unsuccessful", exception);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateExpenseCategory(String token, Long id, ExpenseCategoryRequest request) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            ExpenseCategory expenseCategory = expenseCategoryRepository.findById(id).orElse(null);

            if (expenseCategory == null)
                return ResponseEntity.ok(new ApiResponse("Expense category not found", null, null));

            dumpExpenseCategory(request, expenseCategory, user);

            return ResponseEntity.ok(new ApiResponse("Expense category updated successfully", expenseCategory, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal category update unsuccessful", exception);

        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteExpenseCategory(String token, Long id) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            ExpenseCategory expenseCategory = expenseCategoryRepository.findExpenseCategoryUserId(id, user.getId());

            if (expenseCategory != null)
                expenseCategoryRepository.delete(expenseCategory);
            else
                return ResponseEntity.ok(new ApiResponse("Expense category not found", null, null));

            return ResponseEntity.ok(new ApiResponse("Expense category deleted successfully", null, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal category delete unsuccessful", exception);

        }
    }

    @Override
    public ResponseEntity<ApiResponse> getExpenseCategoryDetails(String token, Long id) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            ExpenseCategory expenseCategory = expenseCategoryRepository.findById(id).orElse(null);

            if (expenseCategory == null)
                return ResponseEntity.ok(new ApiResponse("Expense category not found", null, null));

            return ResponseEntity.ok(new ApiResponse("Expense category retrieved successfully", expenseCategory, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal category update unsuccessful", exception);

        }
    }

    private void dumpExpenseCategory(ExpenseCategoryRequest request, ExpenseCategory expenseCategory, User user) {

        expenseCategory.setName(request.getName());

        expenseCategory.setUser(user);

        expenseCategory.setDescription(request.getDescription());

        expenseCategory.setAmountLimit(request.getAmountLimit());

        expenseCategoryRepository.save(expenseCategory);
    }
}
