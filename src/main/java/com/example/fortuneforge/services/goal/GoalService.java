package com.example.fortuneforge.services.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalRequest;
import org.springframework.http.ResponseEntity;

public interface GoalService {

    public ResponseEntity<ApiResponse> getGoals(String token);

    public ResponseEntity<ApiResponse> createGoal(String token, GoalRequest goalRequest);
}
