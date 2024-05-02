package com.example.fortuneforge.repositories;

import com.example.fortuneforge.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
