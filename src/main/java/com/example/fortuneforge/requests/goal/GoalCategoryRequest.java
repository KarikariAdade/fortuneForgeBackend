package com.example.fortuneforge.requests.goal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalCategoryRequest {


    private Long id;

    @NotEmpty(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    @NotNull(message = "Name field is required")
    private String name;

    private String description;

}
