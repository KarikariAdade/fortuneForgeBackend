package com.example.fortuneforge.services.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalCategoryRequest;
import org.springframework.http.ResponseEntity;

public interface GoalCategoryService {

    ResponseEntity<ApiResponse> getGoalCategories(String token);

    ResponseEntity<ApiResponse> createGoalCategory(String token, GoalCategoryRequest request);

    ResponseEntity<ApiResponse> updateGoalCategory(String token, Long id, GoalCategoryRequest request);

    ResponseEntity<ApiResponse> deleteGoalCategory(String token, Long id);


}
