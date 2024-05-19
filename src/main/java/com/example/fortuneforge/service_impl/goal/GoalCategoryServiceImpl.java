package com.example.fortuneforge.service_impl.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalCategoryRequest;
import com.example.fortuneforge.services.goal.GoalCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GoalCategoryServiceImpl implements GoalCategoryService {
    @Override
    public ResponseEntity<ApiResponse> getGoalCategories(String token) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> createGoalCategory(String token, GoalCategoryRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateGoalCategory(Long id, GoalCategoryRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGoalCategory(Long id) {
        return null;
    }
}
