package com.example.fortuneforge.repositories.budget;

import com.example.fortuneforge.models.BudgetAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetAllocationRepository extends JpaRepository<BudgetAllocation, Long> {

    @Query("select a from BudgetAllocation a where a.budget.id = :budgetId")
    List<BudgetAllocation> getAllocationWithSelectedBudget(Long budgetId);

}
