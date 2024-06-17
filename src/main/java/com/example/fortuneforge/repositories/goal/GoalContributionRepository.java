package com.example.fortuneforge.repositories.goal;

import com.example.fortuneforge.models.GoalContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalContributionRepository extends JpaRepository<GoalContribution, Long> {

    //    @Query
    List<GoalContribution> findAllByUserId(Long userId);

    @Query("select g from GoalContribution g left join User u on g.user.id = u.id where g.user.id = :userId and g.id = :id")
    GoalContribution findByUserId(Long id, Long userId);
}
