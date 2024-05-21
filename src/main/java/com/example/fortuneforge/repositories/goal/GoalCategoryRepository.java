package com.example.fortuneforge.repositories.goal;

import com.example.fortuneforge.models.GoalCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalCategoryRepository extends JpaRepository<GoalCategory, Long> {
    List<GoalCategory> findByUserIdOrderByIdDesc(Long id);
}
