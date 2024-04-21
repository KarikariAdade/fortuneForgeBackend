package com.example.fortuneforge.requests.customValidator;

import com.example.fortuneforge.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailConstraint, String> {

    private final UserRepository userRepository; // Assuming you have a UserRepository

    @Override
    public void initialize(UniqueEmailConstraint uniqueEmailConstraint) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Check if the email already exists in the database
        return !userRepository.existsByEmail(email);
    }
}
