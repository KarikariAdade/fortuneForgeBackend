package com.example.fortuneforge.repositories.goal;

import com.example.fortuneforge.models.GoalContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalContributionRepository extends JpaRepository<GoalContribution, Long> {

//    @Query
    List<GoalContribution> findAllByUserId(Long userId);
}
