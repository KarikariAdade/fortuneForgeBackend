package com.example.fortuneforge.repositories;

import com.example.fortuneforge.models.IncomeCategory;
import com.example.fortuneforge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

    List<IncomeCategory> findByUserIdOrderByIdDesc(Long user_id);

}
