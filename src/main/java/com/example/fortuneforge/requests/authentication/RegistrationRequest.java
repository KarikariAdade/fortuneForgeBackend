package com.example.fortuneforge.requests.authentication;

import com.example.fortuneforge.models.Role;
import com.example.fortuneforge.requests.customValidator.UniqueEmailConstraint;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Name field is required")
    @NotNull(message = "Name field is required")
    @NotBlank(message = "Name field is required")
    private String name;

    @NotEmpty(message = "Email field is required")
    @NotNull(message = "Email field is required")
    @NotBlank(message = "Email field is required")
    @Email(message = "Invalid email address")
    @UniqueEmailConstraint
    private String email;

    @NotEmpty(message = "Password field is required")
    @NotNull(message = "Password field is required")
    @NotBlank(message = "Password field is required")
    @Size(min = 8, message = "Password too short")
    private String password;

    @NotEmpty(message = "Phone field is required")
    @NotBlank(message = "Phone field is required")
    @NotNull(message = "Phone field is required")
    @Size(min = 9, message = "Phone number too short")
    private String phone;

    @NotNull(message = "Role field is required")
    @NotEmpty(message = "Role field is required")
    @NotBlank(message = "Role field is required")
    private String role;
}
