package com.example.fortuneforge.repositories.goal;

import com.example.fortuneforge.models.GoalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalTransactionRepository extends JpaRepository<GoalTransaction, Long> {
}
