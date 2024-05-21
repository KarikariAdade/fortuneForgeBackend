package com.example.fortuneforge.repositories.goal;

import com.example.fortuneforge.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByGoalCategoryId(Long goalCategoryId);

}
