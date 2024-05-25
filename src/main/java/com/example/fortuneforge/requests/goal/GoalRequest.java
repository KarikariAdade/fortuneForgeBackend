package com.example.fortuneforge.requests.goal;

import com.example.fortuneforge.models.GoalCategory;
import com.example.fortuneforge.models.GoalPriority;
import com.example.fortuneforge.models.GoalStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class GoalRequest {

    private Long id;

    @NotEmpty(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    @NotNull(message = "Name field is required")
    private String name;

    private double currentAmount;

    @Min(1)
    private double targetAmount;

    @NotEmpty(message = "End Date field is required")
    @NotBlank(message = "End Date field is required")
    @NotNull(message = "End Date field is required")
    private String endDate;

    @NotEmpty(message = "Start Date field is required")
    @NotBlank(message = "Start Date field is required")
    @NotNull(message = "Start Date field is required")
    private String startDate;

    @Enumerated(EnumType.STRING)
    private GoalPriority priority;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    @NotEmpty(message = "Category Field is required")
    @NotBlank(message = "Category Field is required")
    @NotNull(message = "Category Field is required")
    private String goalCategory;

    private String userId;


}
