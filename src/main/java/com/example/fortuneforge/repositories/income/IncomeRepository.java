package com.example.fortuneforge.repositories.income;

import com.example.fortuneforge.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("select count(i) > 0 from Income i where i.user.id = :user_id and i.name = :name")
    boolean findByUserIdAndName(Long user_id, String name);

    @Query("select i from Income i join i.incomeCategory as category where i.user.id = :user_id order by i.id desc")
    List<Income> findAllByUserIdWithCategories(Long user_id);
}
