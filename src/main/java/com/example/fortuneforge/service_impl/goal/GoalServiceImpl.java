package com.example.fortuneforge.service_impl.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.Goal;
import com.example.fortuneforge.models.GoalCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.goal.GoalCategoryRepository;
import com.example.fortuneforge.repositories.goal.GoalRepository;
import com.example.fortuneforge.requests.goal.GoalRequest;
import com.example.fortuneforge.services.AuthenticationService;
import com.example.fortuneforge.services.SmsService;
import com.example.fortuneforge.services.goal.GoalService;
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
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    private final AuthenticationService authenticationService;

    private final GoalCategoryRepository goalCategoryRepository;

    private final SmsService smsService;

    @Override
    public ResponseEntity<ApiResponse> getGoals(String token) {

        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                List<Goal> goals = goalRepository.findGoalByUserId(user.getId());

                return ResponseEntity.ok(new ApiResponse("User goals retrieved successfully", goals, null));

            }

            return new ResponseEntity<>(new ApiResponse("User goal could not be retrieved.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {
            return CatchErrorResponses.catchErrors("Goals could not be retrieved", exception);
        }


    }

    @Override
    public ResponseEntity<ApiResponse> storeGoal(String token, GoalRequest goalRequest) {

        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                Optional<GoalCategory> goalCategory = goalCategoryRepository.findById(Long.parseLong(goalRequest.getGoalCategory()));

                if (goalCategory.isPresent()) {

                    goalRepository.save(setGoalData(goalRequest, goalCategory, user));

                    smsService.sendSms(user.getPhone(), "You created a new goal with the name " + goalRequest.getName() + "Start Date: " + goalRequest.getStartDate() + " End Date: " + goalRequest.getEndDate());

                    return new ResponseEntity<>(new ApiResponse("Goal added successfully", goalRequest, null), HttpStatus.OK);

                }

                return new ResponseEntity<>(new ApiResponse("Goal Category could not be retrieved.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

            }

            return new ResponseEntity<>(new ApiResponse("User could not be retrieved.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal could not be stored. Kindly try again", exception);

        }
    }


    @Override
    public ResponseEntity<ApiResponse> updateGoal(String token, Long id, GoalRequest goalRequest) {

        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {
                Optional<GoalCategory> goalCategory = goalCategoryRepository.findById(Long.parseLong(goalRequest.getGoalCategory()));

                if (goalCategory.isPresent()) {

                    Goal goal = goalRepository.findById(id).orElse(null);

                    if (goal != null) {

                        updateGoal(goalRequest, goal, goalCategory, user);

                        return ResponseEntity.ok(new ApiResponse("Goal updated successfully.", null, null));
                    }

                    return new ResponseEntity<>(new ApiResponse("Goal not found.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

                }

                return new ResponseEntity<>(new ApiResponse("Goal Category could not be retrieved.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(new ApiResponse("User not found.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal Update failed. Kindly try again", exception);

        }

    }


    @Override
    public ResponseEntity<ApiResponse> deleteGoal(String token, Long id) {

        try {
            User user = authenticationService.retrieveUserFromToken(token);

            if (!isEmpty(user)) {

                goalRepository.findById(id).ifPresent(goalRepository::delete);

                return new ResponseEntity<>(new ApiResponse("Goal deleted successfully.", null, null), HttpStatus.OK);

            }

            return new ResponseEntity<>(new ApiResponse("User could not be retrieved.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Goal could not be deleted", exception);

        }

    }

    @Override
    public ResponseEntity<ApiResponse> getGoalDetails(String token, Long id) {
        User user = authenticationService.retrieveUserFromToken(token);

        if (!isEmpty(user)) {

            Goal goal = goalRepository.findByUserId(user.getId(), id);

            if (!isEmpty(goal)) {

                return new ResponseEntity<>(new ApiResponse("Goal retrieved successfully", goal, null), HttpStatus.OK);

            }

            return new ResponseEntity<>(new ApiResponse("Goal could not be retrieved.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new ApiResponse("User could not be retrieved.", null, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Goal setGoalData(GoalRequest goalRequest, Optional<GoalCategory> goalCategory, User user) {
        Goal goal = new Goal();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        goal.setName(goalRequest.getName());
        goal.setCurrentAmount(0);
        goal.setTargetAmount(goalRequest.getTargetAmount());
        goal.setPriority(goalRequest.getPriority());
        goal.setEndDate(LocalDate.parse(goalRequest.getEndDate(), formatter));
        goal.setStartDate(LocalDate.parse(goalRequest.getStartDate(), formatter));
        goal.setStatus(goalRequest.getStatus());
        goal.setGoalCategory(goalCategory.get());
        goal.setUser(user);

        return goal;
    }

    private void updateGoal(GoalRequest goalRequest, Goal goal, Optional<GoalCategory> goalCategory, User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        goal.setName(goalRequest.getName());
        goal.setCurrentAmount(0);
        goal.setTargetAmount(goalRequest.getTargetAmount());
        goal.setPriority(goalRequest.getPriority());
        goal.setEndDate(LocalDate.parse(goalRequest.getEndDate(), formatter));
        goal.setStartDate(LocalDate.parse(goalRequest.getStartDate(), formatter));
        goal.setStatus(goalRequest.getStatus());
        goal.setGoalCategory(goalCategory.get());
        goal.setUser(user);

        goalRepository.save(goal);
    }
}
