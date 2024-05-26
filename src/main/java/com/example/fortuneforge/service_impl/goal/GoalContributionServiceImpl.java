package com.example.fortuneforge.service_impl.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.GoalContribution;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.goal.GoalContributionRepository;
import com.example.fortuneforge.requests.goal.GoalContributionRequest;
import com.example.fortuneforge.requests.goal.GoalRequest;
import com.example.fortuneforge.services.AuthenticationService;
import com.example.fortuneforge.services.goal.GoalContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.jsonwebtoken.lang.Objects.isEmpty;

@Service
@RequiredArgsConstructor
public class GoalContributionServiceImpl implements GoalContributionService {

    private final AuthenticationService authenticationService;

    private final GoalContributionRepository goalContributionRepository;
    @Override
    public ResponseEntity<ApiResponse> getGoalContributions(String token) {

        try {

            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                List<GoalContribution> goalContribution = goalContributionRepository.findAllByUserId(user.getId());

                return ResponseEntity.ok(new ApiResponse("Goal contributions retrieved successfully", goalContribution, null));

            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal Contributions not found", exception);

        }


    }

    @Override
    public ResponseEntity<ApiResponse> storeGoalContribution(String token, GoalContributionRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateGoalContribution(String token, Long id, GoalContributionRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGoalContribution(String token, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getGoalContributionDetails(String token, Long id) {
        return null;
    }
}
