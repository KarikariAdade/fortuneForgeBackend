package com.example.fortuneforge.controllers.goal;


import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.goal.GoalCategoryRequest;
import com.example.fortuneforge.service_impl.goal.GoalCategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goal/category")
@RequiredArgsConstructor
public class GoalCategoryController {

    private final GoalCategoryServiceImpl goalCategoryService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getGoalCategories(@RequestHeader String token) {

        return this.goalCategoryService.getGoalCategories(token);

    }

    @PostMapping("/store")
    public ResponseEntity<ApiResponse> addGoalCategory(@RequestHeader String token, @RequestBody GoalCategoryRequest request) {

        return this.goalCategoryService.createGoalCategory(token, request);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateGoalCategory(@PathVariable Long id, @RequestBody GoalCategoryRequest request) {

        return this.goalCategoryService.updateGoalCategory(id, request);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteGoalCategory(@PathVariable Long id) {

        return this.goalCategoryService.deleteGoalCategory(id);
    }

}
