package com.example.fortuneforge.repositories.budget;

import com.example.fortuneforge.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("select b from Budget b where b.user.id = :userId")
    List<Budget> listBudgetByUserId(Long userId);

}
