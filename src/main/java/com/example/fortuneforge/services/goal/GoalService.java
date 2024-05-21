package com.example.fortuneforge.services.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalRequest;
import org.springframework.http.ResponseEntity;

public interface GoalService {

    ResponseEntity<ApiResponse> getGoals(String token);

    ResponseEntity<ApiResponse> storeGoal(String token, GoalRequest goalRequest);

    ResponseEntity<ApiResponse> updateGoal(String token, Long id, GoalRequest goalRequest);

    ResponseEntity<ApiResponse> deleteGoal(String token, Long id);

    ResponseEntity<ApiResponse> getGoalDetails(String token, Long id);
}
