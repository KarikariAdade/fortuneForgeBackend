package com.example.fortuneforge.controllers;

import com.example.fortuneforge.requests.authentication.LoginRequest;
import com.example.fortuneforge.requests.authentication.RegistrationRequest;
import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegistrationRequest request) {

        System.out.println(request.toString());

        return authenticationService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request) {

        System.out.println(request.toString());

        return authenticationService.loginUser(request);

    }
}
