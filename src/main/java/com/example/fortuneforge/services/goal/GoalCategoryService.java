package com.example.fortuneforge.services.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalCategoryRequest;
import org.springframework.http.ResponseEntity;

public interface GoalCategoryService {

    public ResponseEntity<ApiResponse> getGoalCategories (String token);

    public ResponseEntity<ApiResponse> createGoalCategory (String token, GoalCategoryRequest request);

    public ResponseEntity<ApiResponse> updateGoalCategory (Long id, GoalCategoryRequest request);

    public ResponseEntity<ApiResponse> deleteGoalCategory (Long id);


}
