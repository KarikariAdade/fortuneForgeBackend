package com.example.fortuneforge.requests.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)

public @interface UniqueEmailConstraint {

    String message() default "Email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
