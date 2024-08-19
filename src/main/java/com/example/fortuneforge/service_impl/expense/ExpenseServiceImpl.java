package com.example.fortuneforge.service_impl.expense;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.Expense;
import com.example.fortuneforge.models.ExpenseCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.expense.ExpenseCategoryRepository;
import com.example.fortuneforge.repositories.expense.ExpenseRepository;
import com.example.fortuneforge.requests.expense.ExpenseRequest;
import com.example.fortuneforge.services.AuthenticationService;
import com.example.fortuneforge.services.expense.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.jsonwebtoken.lang.Objects.isEmpty;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final AuthenticationService authenticationService;

    private final ExpenseRepository expenseRepository;

    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public ResponseEntity<ApiResponse> listExpenses(String token) {
        try {
            User user = authenticationService.validateUserFromToken(token);

            List<Expense> expenseList = expenseRepository.findAllByUserId(user.getId());

            if (expenseList == null)
                return new ResponseEntity<>(new ApiResponse("Expenses not found for selected user", null, null), HttpStatus.INTERNAL_SERVER_ERROR);
            else
                return ResponseEntity.ok(new ApiResponse("Expenses retrieved successfully", expenseList, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Expense could not be retrieved", exception);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> storeExpense(String token, ExpenseRequest request) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            Expense expense = new Expense();

            Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(Long.valueOf(request.getExpenseCategory()));

            if (expenseCategory.isPresent()) {
                dumpExpenseData(request, expense, user, expenseCategory);

                return ResponseEntity.ok(new ApiResponse("Expense created successfully", expense, null));
            }

            return new ResponseEntity<>(new ApiResponse("Expense category not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Expense could not be retrieved", exception);
        }
    }

    private void dumpExpenseData(ExpenseRequest request, Expense expense, User user, Optional<ExpenseCategory> expenseCategory) {
        expense.setName(request.getName());
        expense.setUser(user);
        expense.setDescription(request.getDescription());
        expense.setExpenseCategory(expenseCategory.get());
        expense.setAmount(request.getAmount());

        expenseRepository.save(expense);
    }

    @Override
    public ResponseEntity<ApiResponse> updateExpense(String token, Long id, ExpenseRequest request) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            Expense expense = expenseRepository.findById(id).orElse(null);

            Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(Long.valueOf(request.getExpenseCategory()));


            if (!isEmpty(expense) && expenseCategory.isPresent()) {


                dumpExpenseData(request, expense, user, expenseCategory);

                return ResponseEntity.ok(new ApiResponse("Expense updated successfully", expense, null));

            }

            return new ResponseEntity<>(new ApiResponse("Expense not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Expense could not be updated", exception);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteExpense(String token, Long id) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            Optional<Expense> expense = expenseRepository.findByUserIdAndExpenseId(user.getId(), id);

            if (expense.isPresent()) {
                expenseRepository.deleteById(id);

                return ResponseEntity.ok(new ApiResponse("Expense deleted successfully", null, null));
            }

            return new ResponseEntity<>(new ApiResponse("Expense not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Expense could not be deleted", exception);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getExpenseDetails(String token, Long id) {
        try {

            User user = authenticationService.validateUserFromToken(token);

            Optional<Expense> expense = expenseRepository.findByUserIdAndExpenseId(user.getId(), id);

            if (expense.isPresent()) {

                return ResponseEntity.ok(new ApiResponse("Expense retrieved successfully", expense, null));
            }

            return new ResponseEntity<>(new ApiResponse("Expense not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Expense could not be retrieved", exception);
        }
    }
}
