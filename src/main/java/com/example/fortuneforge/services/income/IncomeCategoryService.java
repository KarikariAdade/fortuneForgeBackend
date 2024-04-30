package com.example.fortuneforge.services.income;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.IncomeCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.IncomeCategoryRepository;
import com.example.fortuneforge.repositories.UserRepository;
import com.example.fortuneforge.requests.income.IncomeCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IncomeCategoryService {

    private final IncomeCategoryRepository incomeCategoryRepository;

    private final UserRepository userRepository;

    public ResponseEntity<ApiResponse> getIncomeCategories(@RequestBody IncomeCategoryRequest request) {

        try {

            List<IncomeCategory> incomeCategories = incomeCategoryRepository.findByUserIdOrderByIdDesc(request.getUserId());

            return ResponseEntity.ok(new ApiResponse("User categories retrieved successfully", incomeCategories, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Categories not found", exception);
        }

    }

    public ResponseEntity<ApiResponse> addIncomeCategory(@RequestBody @Valid IncomeCategoryRequest request) {

        try {

            IncomeCategory incomeCategory = new IncomeCategory();

            Optional<User> user = userRepository.findById(request.getUserId());

            if (user.isPresent()) {

                incomeCategory.setUser(user.get());

                incomeCategory.setName(request.getName());

                incomeCategory.setDescription(request.getDescription());

                incomeCategoryRepository.save(incomeCategory);

                return ResponseEntity.ok(new ApiResponse("User categories retrieved successfully", incomeCategory, null));
            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Categories not found", exception);

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
