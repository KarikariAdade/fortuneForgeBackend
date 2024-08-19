package com.example.fortuneforge.repositories.budget;

import com.example.fortuneforge.models.BudgetIncome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetIncomeRepository extends JpaRepository<BudgetIncome, Long> {
}
