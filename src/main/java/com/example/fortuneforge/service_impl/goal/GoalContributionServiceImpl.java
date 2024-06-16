package com.example.fortuneforge.service_impl.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.Goal;
import com.example.fortuneforge.models.GoalContribution;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.goal.GoalContributionRepository;
import com.example.fortuneforge.repositories.goal.GoalRepository;
import com.example.fortuneforge.requests.goal.GoalContributionRequest;
import com.example.fortuneforge.services.AuthenticationService;
import com.example.fortuneforge.services.goal.GoalContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static io.jsonwebtoken.lang.Objects.isEmpty;

@Service
@RequiredArgsConstructor
public class GoalContributionServiceImpl implements GoalContributionService {

    private final AuthenticationService authenticationService;

    private final GoalContributionRepository goalContributionRepository;

    private final GoalRepository goalRepository;

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
    public ResponseEntity<ApiResponse> storeContribution(String token, GoalContributionRequest request) {

        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                Optional<Goal> goal = goalRepository.findById(Long.valueOf(request.getGoalId()));

                if (goal.isPresent()) {

                    double goalAmountRemaining = goal.get().getTargetAmount() - goal.get().getCurrentAmount();

                    if (goalAmountRemaining < request.getAmount())
                        return new ResponseEntity<>(new ApiResponse("Contribution amount exceeds the current remaining amount for the selected goal", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

                    GoalContribution goalContribution = new GoalContribution();

                    goalContributionRepository.save(generateGoalContributionData(request, goalContribution, user, goal));

                    return ResponseEntity.ok(new ApiResponse("Goal contribution created successfully", goalContribution, null));

                }

                return new ResponseEntity<>(new ApiResponse("Selected Goal not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal Contributions could not be created. Kindly try again.", exception);

        }

    }

    @Override
    public ResponseEntity<ApiResponse> updateGoalContribution(String token, Long id, GoalContributionRequest request) {
        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                Optional<GoalContribution> goalContribution = goalContributionRepository.findById(id);

                Optional<Goal> goal = goalRepository.findById(Long.valueOf(request.getGoalId()));

                if (goalContribution.isPresent() && goal.isPresent()) {

                    double goalCurrentAmount = (goal.get().getCurrentAmount() - goalContribution.get().getAmount()) + request.getAmount();

                    double goalAmountRemaining = goal.get().getTargetAmount() - goalCurrentAmount;

                    if (goalAmountRemaining < request.getAmount())
                        return new ResponseEntity<>(new ApiResponse("Contribution amount exceeds the current remaining amount for the selected goal", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

                    goalContributionRepository.save(generateGoalContributionData(request, goalContribution.get(), user, goal));

                    goal.get().setCurrentAmount(goalCurrentAmount);

                    goalRepository.save(goal.get());

                    return ResponseEntity.ok(new ApiResponse("Goal Contribution updated successfully", goalContribution.get(), null));

                }

                return new ResponseEntity<>(new ApiResponse("Goal Contribution not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);


            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal Contributions could not be updated. Kindly try again.", exception);

        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGoalContribution(String token, Long id) {
        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {
                Optional<GoalContribution> goalContribution = goalContributionRepository.findById(id);


                if (goalContribution.isPresent()) {

                    Optional<Goal> goal = goalRepository.findById(goalContribution.get().getGoal().getId());

                    if (goal.isPresent()) {
                        goal.get().setCurrentAmount(goal.get().getCurrentAmount() - goalContribution.get().getAmount());

                        goalRepository.save(goal.get());
                    }

                    goalContributionRepository.delete(goalContribution.get());

                    return ResponseEntity.ok(new ApiResponse("Goal Contribution deleted successfully", goalContribution.get(), null));

                }

                return new ResponseEntity<>(new ApiResponse("Goal Contribution not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal Contribution not retrieved. Kindly try again.", exception);

        }
    }

    @Override
    public ResponseEntity<ApiResponse> getGoalContributionDetails(String token, Long id) {
        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                GoalContribution goalContribution = goalContributionRepository.findByUserId(id, user.getId());

                if (isEmpty(goalContribution))
                    return new ResponseEntity<>(new ApiResponse("Goal Contribution not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

                return ResponseEntity.ok(new ApiResponse("Goal contribution retrieved", goalContribution, null));

            }

            return new ResponseEntity<>(new ApiResponse("User not found", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal Contribution not retrieved. Kindly try again.", exception);

        }

    }

    private static GoalContribution generateGoalContributionData(GoalContributionRequest request, GoalContribution goalContribution, User user, Optional<Goal> goal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate = LocalDate.parse(request.getDate(), formatter);

        goalContribution.setUser(user);

        goalContribution.setGoal(goal.get());

        goalContribution.setAmount(request.getAmount());

        goalContribution.setDescription(request.getDescription());

        goalContribution.setDate(startDate);

        return goalContribution;
    }
}
