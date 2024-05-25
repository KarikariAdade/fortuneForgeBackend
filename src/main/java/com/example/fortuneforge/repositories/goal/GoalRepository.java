package com.example.fortuneforge.repositories.goal;

import com.example.fortuneforge.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByGoalCategoryUserId(Long goalCategoryId);

//    @Query("select i from Income i join i.incomeCategory as category where i.user.id = :user_id order by i.id desc")
    @Query("select g from Goal g " +
        "left join fetch g.transactions t " +
        "left join fetch g.contributions c " +
        "where g.id = :goalId and g.user.id = :userId")
    Goal findByUserId(Long userId, Long goalId);

    @Query("select g from Goal g where g.user.id = :id")
    List<Goal> findGoalByUserId(Long id);
}
