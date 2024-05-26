package com.example.fortuneforge.service_impl.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.GoalCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.goal.GoalCategoryRepository;
import com.example.fortuneforge.requests.goal.GoalCategoryRequest;
import com.example.fortuneforge.services.AuthenticationService;
import com.example.fortuneforge.services.goal.GoalCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.jsonwebtoken.lang.Objects.isEmpty;

@Service
@RequiredArgsConstructor
public class GoalCategoryServiceImpl implements GoalCategoryService {

    private final AuthenticationService authenticationService;

    private final GoalCategoryRepository goalCategoryRepository;

    @Override
    public ResponseEntity<ApiResponse> getGoalCategories(String token) {

        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {
                List<GoalCategory> category = goalCategoryRepository.findByUserIdOrderByIdDesc(user.getId());

                return ResponseEntity.ok(new ApiResponse("User categories retrieved successfully", category, null));
            }

            return ResponseEntity.ok(new ApiResponse("User not found", null, null));

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal Categories not found", exception);

        }

    }

    @Override
    public ResponseEntity<ApiResponse> createGoalCategory(String token, GoalCategoryRequest request) {

        try {

            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                GoalCategory goalCategory = new GoalCategory();

                goalCategory.setUser(user);

                goalCategory.setName(request.getName());

                goalCategory.setDescription(request.getDescription());

                goalCategoryRepository.save(goalCategory);

                return ResponseEntity.ok(new ApiResponse("Goal category created successfully", goalCategory, null));

            }

            return ResponseEntity.ok(new ApiResponse("User not found", null, null));

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal category could not be created", exception);

        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateGoalCategory(String token, Long id, GoalCategoryRequest request) {

        try {

            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                GoalCategory category = goalCategoryRepository.findById(id).orElse(null);

                if (category != null) {

                    category.setName(request.getName());

                    category.setDescription(request.getDescription());

                    goalCategoryRepository.save(category);

                    return ResponseEntity.ok(new ApiResponse("Goal category updated successfully", category, null));

                }

            }

            return ResponseEntity.ok(new ApiResponse("User not found", null, null));

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal category update unsuccessful", exception);

        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGoalCategory(String token, Long id) {

        try {

            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {
                GoalCategory category = goalCategoryRepository.findById(id).orElse(null);

                if (!isEmpty(category) && !isEmpty(category.getGoals())) {

                    return new ResponseEntity<>(new ApiResponse("Category has goals and cannot be deleted.", category.getGoals(), null), HttpStatus.INTERNAL_SERVER_ERROR);

                }else {

                    goalCategoryRepository.deleteById(category.getId());

                    return ResponseEntity.ok(new ApiResponse("Category deleted successfully", category, null));

                }

            }

            return ResponseEntity.ok(new ApiResponse("User not found", null, null));

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal category deletion failed", exception);

        }

    }
}
