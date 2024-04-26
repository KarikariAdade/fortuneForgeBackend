package com.example.fortuneforge.controllers;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.UserRepository;
import com.example.fortuneforge.requests.authentication.LoginRequest;
import com.example.fortuneforge.requests.authentication.PasswordResetRequest;
import com.example.fortuneforge.requests.authentication.RegistrationRequest;
import com.example.fortuneforge.services.AuthEmailService;
import com.example.fortuneforge.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegistrationRequest request) {

        try {

            System.out.println(request.toString());

            return authenticationService.registerUser(request);

        }catch (Exception exception) {

            String errorMessage = "Failed to login user: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request) {

        System.out.println(request.toString());

        return authenticationService.loginUser(request);

    }

    @PostMapping("/password/forgot")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody User user) {

        return authenticationService.forgotPassword(user);

    }

    @PostMapping("/password/reset")
    public ResponseEntity<ApiResponse> passwordReset(@RequestBody @Valid PasswordResetRequest request) {
        return authenticationService.passwordReset(request);
    }
}
