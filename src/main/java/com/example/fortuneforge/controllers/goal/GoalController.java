package com.example.fortuneforge.controllers.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalRequest;
import com.example.fortuneforge.service_impl.goal.GoalServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goal")
@RequiredArgsConstructor
@Tag(name = "Goals", description = "Goal Management")
public class GoalController {

    private final GoalServiceImpl goalService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllGoals(@RequestHeader("Authorization") String token) {
        return goalService.getGoals(token);
    }


    @PostMapping("store")
    public ResponseEntity<ApiResponse> store(@RequestHeader("Authorization") String token, @RequestBody @Valid GoalRequest request) {
        return goalService.storeGoal(token, request);
    }

    @GetMapping("details/{id}")
    public ResponseEntity<ApiResponse> getGoalDetails(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return goalService.getGoalDetails(token, id);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateGoal(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid GoalRequest request) {
        return goalService.updateGoal(token, id, request);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteGoal(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return goalService.deleteGoal(token, id);
    }

}
