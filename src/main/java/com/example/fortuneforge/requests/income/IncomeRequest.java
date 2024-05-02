package com.example.fortuneforge.requests.income;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class IncomeRequest {

    private String name;

    private String userId;

    private String categoryId;

    private double amount;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isRecurring;

    private String description;


}
