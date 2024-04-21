package com.example.fortuneforge.requests.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotEmpty(message = "Email field is required")
    @NotBlank(message = "Email field is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Password field is required")
    @NotBlank(message = "Password field is required")
    private String password;
}
