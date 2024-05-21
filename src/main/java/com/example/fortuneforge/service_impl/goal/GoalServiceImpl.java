package com.example.fortuneforge.service_impl.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalRequest;
import com.example.fortuneforge.services.goal.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {
    @Override
    public ResponseEntity<ApiResponse> getGoals(String token) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> storeGoal(String token, GoalRequest goalRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateGoal(String token, Long id, GoalRequest goalRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGoal(String token, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getGoalDetails(String token, Long id) {
        return null;
    }

}
