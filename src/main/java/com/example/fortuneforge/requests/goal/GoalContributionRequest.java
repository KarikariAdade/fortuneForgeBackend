package com.example.fortuneforge.requests.goal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoalContributionRequest {

    @NotEmpty(message = "Goal field is required")
    @NotBlank(message = "Goal field is required")
    @NotNull(message = "Goal field is required")
    private String goalId;

    private String userId;

    @Min(1)
    private double amount;

    @NotEmpty(message = "Date field is required")
    @NotBlank(message = "Date field is required")
    @NotNull(message = "Date field is required")
    private String date;

    private String description;

}
