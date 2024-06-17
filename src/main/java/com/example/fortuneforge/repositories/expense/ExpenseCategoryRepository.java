package com.example.fortuneforge.repositories.expense;

import com.example.fortuneforge.models.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    List<ExpenseCategory> findAllByUserId(Long userId);

    @Query("select e from ExpenseCategory e where e.user.id = :user_id and e.id = :id")
    ExpenseCategory findExpenseCategoryUserId(Long id, Long user_id);

}
