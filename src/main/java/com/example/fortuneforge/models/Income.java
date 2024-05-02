package com.example.fortuneforge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "income_category_id")
    private IncomeCategory incomeCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String name;

    private double amount;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isRecurring = false;
}
