package com.example.fortuneforge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String name;

    private double targetAmount;

    private double currentAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goal_category_id")
//    @JsonIgnore
    private GoalCategory goalCategory;

    @Enumerated(value = EnumType.STRING)
    private GoalStatus status;

    @Enumerated(value = EnumType.STRING)
    private GoalPriority priority;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private Set<GoalTransaction> transactions;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private Set<GoalContribution> contributions;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
