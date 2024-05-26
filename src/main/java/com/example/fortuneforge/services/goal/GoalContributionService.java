package com.example.fortuneforge.services.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalContributionRequest;
import org.springframework.http.ResponseEntity;

public interface GoalContributionService {

    ResponseEntity<ApiResponse> getGoalContributions(String token);

    ResponseEntity<ApiResponse> storeGoalContribution(String token, GoalContributionRequest goalRequest);

    ResponseEntity<ApiResponse> updateGoalContribution(String token, Long id, GoalContributionRequest goalRequest);

    ResponseEntity<ApiResponse> deleteGoalContribution(String token, Long id);

    ResponseEntity<ApiResponse> getGoalContributionDetails(String token, Long id);
}
