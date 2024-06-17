package com.example.fortuneforge.repositories.expense;

import com.example.fortuneforge.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("select e from Expense e where e.user.id = :id")
    List<Expense> findAllByUserId(Long id);
}
