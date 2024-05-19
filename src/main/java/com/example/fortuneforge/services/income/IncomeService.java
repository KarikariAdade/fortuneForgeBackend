package com.example.fortuneforge.services.income;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.Income;
import com.example.fortuneforge.models.IncomeCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.income.IncomeCategoryRepository;
import com.example.fortuneforge.repositories.income.IncomeRepository;
import com.example.fortuneforge.requests.income.IncomeRequest;
import com.example.fortuneforge.services.AuthenticationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeCategoryService incomeCategoryService;

    private final AuthenticationService authenticationService;

    private final IncomeRepository incomeRepository;

    private final IncomeCategoryRepository incomeCategoryRepository;

    public ResponseEntity<ApiResponse> getAllIncome (@RequestHeader("Authorization") String token) {

        User user = authenticationService.retrieveUserFromToken(token);

        if (user == null)
            return new ResponseEntity<>(new ApiResponse("User not found", token, null), HttpStatus.INTERNAL_SERVER_ERROR);

        List<Income> incomeList = incomeRepository.findAllByUserIdWithCategories(user.getId());

        return ResponseEntity.ok(new ApiResponse("retriving user", incomeList, null));


    }

    public ResponseEntity<ApiResponse> addIncome (String token, @RequestBody IncomeRequest request) {

        try {
            User user = authenticationService.retrieveUserFromToken(token);

            Optional<IncomeCategory> category = incomeCategoryRepository.findById(Long.valueOf(request.getIncomeCategory()));


            if (category.isEmpty())
                return new ResponseEntity<>(new ApiResponse("Category not found", token, null), HttpStatus.INTERNAL_SERVER_ERROR);


            if (user == null)
                return new ResponseEntity<>(new ApiResponse("User not found", token, null), HttpStatus.INTERNAL_SERVER_ERROR);


            boolean income = incomeRepository.findByUserIdAndName(user.getId(), request.getName());

            if (income)
                return new ResponseEntity<>(new ApiResponse("Income with the same name already exist", token, null), HttpStatus.INTERNAL_SERVER_ERROR);

            Income savedIncome = incomeRepository.save(prepareIncomeData(request, user, category));

            return ResponseEntity.ok(new ApiResponse("Income Added successfully", savedIncome, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Income could not be added", exception);

        }


    }

    public ResponseEntity<ApiResponse> updateIncome (String token, @PathVariable String incomeId, @RequestBody IncomeRequest request) {

        try {

            Income income = incomeRepository.findById(Long.valueOf(incomeId)).get();

            User user = authenticationService.retrieveUserFromToken(token);

            if (isEmpty(income)) {
                return new ResponseEntity<>(new ApiResponse("Income not found.", request, null ), HttpStatus.OK);
            }

            if (user == null) {

                return new ResponseEntity<>(new ApiResponse("User not found.", request, null ), HttpStatus.OK);

            }

            Optional<IncomeCategory> incomeCategory = incomeCategoryRepository.findById(Long.valueOf(request.getIncomeCategory()));

            if (incomeCategory.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("Income Category not found.", request, null ), HttpStatus.OK);
            }

            incomeRepository.save(generateIncomeUpdateData(request, income, user, incomeCategory));

            return new ResponseEntity<>(new ApiResponse("Income updated successfully.", income, null ), HttpStatus.OK);


        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Income could not be updated", exception);

        }

    }

    public ResponseEntity<ApiResponse> deleteIncome (String incomeId){

        try {
            Income income = incomeRepository.findById(Long.valueOf(incomeId)).get();

            if(!isEmpty(income)){

                incomeRepository.delete(income);

                return new ResponseEntity<>(new ApiResponse("Income deleted successfully.", income, null), HttpStatus.OK);

            }

            return new ResponseEntity<>(new ApiResponse("Income not found.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Income could not be deleted", exception);

        }

    }

    public ResponseEntity<ApiResponse> deleteBulk(List<HashMap<?, Object>> requests) {

        try {

            requests.forEach(request -> {

                Object object = request.get("id");

                Long id = Long.parseLong(object.toString());

                incomeRepository.findById(id).ifPresent(incomeRepository::delete);

            });

            return new ResponseEntity<>(new ApiResponse("Items deleted successfully", null, null), HttpStatus.OK);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Income Items could not be deleted. Kindly try again", exception);

        }

    }

    private Income generateIncomeUpdateData(IncomeRequest request, Income income, User user, Optional<IncomeCategory> incomeCategory) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        income.setUser(user);

        income.setName(request.getName());

        income.setAmount(Double.parseDouble(request.getAmount()));

        income.setStartDate(LocalDate.parse(request.getStartDate(), formatter));

        income.setDescription(request.getDescription());

        income.setRecurring(request.isRecurring());

        income.setEndDate(LocalDate.parse(request.getEndDate(), formatter));

        income.setIncomeCategory(incomeCategory.get());

        return income;
    }

    private static Income prepareIncomeData(IncomeRequest request, User user, Optional<IncomeCategory> category) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Income income = new Income();

        income.setUser(user);

        income.setName(request.getName());

        income.setAmount(Double.parseDouble(request.getAmount()));

        income.setStartDate(LocalDate.parse(request.getStartDate(), formatter));

        income.setDescription(request.getDescription());

        income.setRecurring(request.isRecurring());

        income.setEndDate(LocalDate.parse(request.getEndDate(), formatter));

        income.setIncomeCategory(category.get());

        return income;
    }


}
