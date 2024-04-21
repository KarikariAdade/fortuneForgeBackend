package com.example.fortuneforge.requests.authentication;

import com.example.fortuneforge.models.Role;
import com.example.fortuneforge.requests.customValidator.UniqueEmailConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    private String name;

    @NotEmpty(message = "Email field is required")
    @NotBlank(message = "Email field is required")
    @Email(message = "Invalid email address")
    @UniqueEmailConstraint
    private String email;

    @NotEmpty(message = "Password field is required")
    @NotBlank(message = "Password field is required")
    @Size(min = 8, message = "Password too short")
    private String password;

    @NotEmpty(message = "Phone field is required")
    @NotBlank(message = "Phone field is required")
    @Size(min = 9, message = "Phone number too short")
    private String phone;

    private Role role;
}
