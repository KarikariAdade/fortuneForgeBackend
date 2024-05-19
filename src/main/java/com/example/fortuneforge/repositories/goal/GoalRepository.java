package com.example.fortuneforge.repositories.goal;

import com.example.fortuneforge.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
