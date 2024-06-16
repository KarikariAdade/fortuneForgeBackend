package com.example.fortuneforge.repositories.expense;

import com.example.fortuneforge.models.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
}
