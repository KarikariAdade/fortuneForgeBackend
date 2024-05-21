package com.example.fortuneforge.controllers.goal;


import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.models.GoalCategory;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.goal.GoalCategoryRepository;
import com.example.fortuneforge.requests.goal.GoalCategoryRequest;
import com.example.fortuneforge.service_impl.goal.GoalCategoryServiceImpl;
import com.example.fortuneforge.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goal/category")
@RequiredArgsConstructor
public class GoalCategoryController {

    private final GoalCategoryServiceImpl goalCategoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getGoalCategories(@RequestHeader("Authorization") String token) {

        return this.goalCategoryService.getGoalCategories(token);

    }

    @PostMapping("store")
    public ResponseEntity<ApiResponse> addGoalCategory(@RequestHeader("Authorization") String token, @RequestBody @Valid GoalCategoryRequest request) {

        return this.goalCategoryService.createGoalCategory(token, request);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateGoalCategory(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody GoalCategoryRequest request) {

        return this.goalCategoryService.updateGoalCategory(token, id, request);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteGoalCategory(@RequestHeader("Authorization") String token, @PathVariable Long id) {

        return this.goalCategoryService.deleteGoalCategory(token, id);
    }

}