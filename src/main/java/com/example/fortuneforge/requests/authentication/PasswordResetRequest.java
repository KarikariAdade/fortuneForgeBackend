package com.example.fortuneforge.requests.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordResetRequest {

    @NotEmpty(message = "Token field is required")
    @NotNull(message = "Token field is required")
    @NotBlank(message = "Token field is required")
    private String token;

    @NotEmpty(message = "Password field is required")
    @NotNull(message = "Password field is required")
    @NotBlank(message = "Password field is required")
    @Size(min = 8, message = "Password too short")
    private String password;

}
