package com.example.fortuneforge.services.income;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.IncomeCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.income.IncomeCategoryRepository;
import com.example.fortuneforge.repositories.UserRepository;
import com.example.fortuneforge.requests.income.IncomeCategoryRequest;
import com.example.fortuneforge.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import static io.jsonwebtoken.lang.Objects.isEmpty;

@RequiredArgsConstructor
@Service
public class IncomeCategoryService {

    private final IncomeCategoryRepository incomeCategoryRepository;

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    public ResponseEntity<ApiResponse> getIncomeCategories(String token) {

       User user = authenticationService.retrieveUserFromToken(token);

        try {

            if (!isEmpty(user)) {

                List<IncomeCategory> incomeCategories = incomeCategoryRepository.findByUserIdOrderByIdDesc(user.getId());

                return ResponseEntity.ok(new ApiResponse("User categories retrieved successfully", incomeCategories, null));

            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Categories not found", exception);
        }

    }

    public ResponseEntity<ApiResponse> addIncomeCategory(String token, @RequestBody @Valid IncomeCategoryRequest request) {

        try {

            User user = authenticationService.retrieveUserFromToken(token);

            IncomeCategory incomeCategory = new IncomeCategory();

            if (!isEmpty(user)) {

                incomeCategory.setUser(user);

                incomeCategory.setName(request.getName());

                incomeCategory.setDescription(request.getDescription());

                incomeCategoryRepository.save(incomeCategory);

                return ResponseEntity.ok(new ApiResponse("User category created successfully", incomeCategory, null));
            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Category could not be created", exception);

        }

    }

    public ResponseEntity<ApiResponse> updateIncomeCategory(@PathVariable Long categoryId, @RequestBody @Valid IncomeCategoryRequest request) {

        try {
            Optional<IncomeCategory> incomeCategory = incomeCategoryRepository.findById(categoryId);

            if (incomeCategory.isPresent()) {

                incomeCategory.get().setName(request.getName());

                incomeCategory.get().setDescription(request.getDescription());

                incomeCategoryRepository.save(incomeCategory.get());

                return ResponseEntity.ok(new ApiResponse("User categories retrieved successfully", incomeCategory.get(), null));
            }

            return new ResponseEntity<>(new ApiResponse("Income category not found", null, null), HttpStatus.OK);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Income category not found", exception);

        }

    }


    public ResponseEntity<ApiResponse> deleteIncomeCategory(@PathVariable Long categoryId) {

        try {

            Optional<IncomeCategory> incomeCategory = incomeCategoryRepository.findById(categoryId);

            if (incomeCategory.isPresent()) {

                incomeCategoryRepository.delete(incomeCategory.get());

                return ResponseEntity.ok(new ApiResponse("Income category deleted successfully", null, null));
            }

            return new ResponseEntity<>(new ApiResponse("Income category not found", null, null), HttpStatus.OK);

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Income category not found", exception);
        }

    }

}
