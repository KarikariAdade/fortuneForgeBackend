package com.example.fortuneforge.controllers.goal;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalContributionRequest;
import com.example.fortuneforge.service_impl.goal.GoalContributionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("goal/contributions")
public class GoalContributionController {

    private final GoalContributionServiceImpl goalContributionService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getGoalContributions(@RequestHeader("Authorization") String token) {
        System.out.println("goal contributors");
        return goalContributionService.getGoalContributions(token);
    }

    @PostMapping("store")
    public ResponseEntity<ApiResponse> storeGoalContribution(@RequestHeader("Authorization") String token, @RequestBody @Valid GoalContributionRequest request) {
        return goalContributionService.storeContribution(token, request);
    }

    @GetMapping("details/{id}")
    public ResponseEntity<ApiResponse> getGoalContributionDetails(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return goalContributionService.getGoalContributionDetails(token, id);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateGoalContribution(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid GoalContributionRequest request) {
        return goalContributionService.updateGoalContribution(token, id, request);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteGoalContribution(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return goalContributionService.deleteGoalContribution(token, id);
    }



}
