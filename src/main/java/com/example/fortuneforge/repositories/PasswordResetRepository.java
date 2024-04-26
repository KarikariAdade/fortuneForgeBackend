package com.example.fortuneforge.repositories;

import com.example.fortuneforge.models.PasswordResets;
import com.example.fortuneforge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordResets, Long> {

    Optional<PasswordResets> findPasswordResetsByToken(String token);

    void deleteAllByUser(User user);

}
