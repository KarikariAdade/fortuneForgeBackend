package com.example.fortuneforge.services;

import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.UserRepository;
import com.example.fortuneforge.requests.authentication.RegistrationRequest;
import com.example.fortuneforge.response.ApiResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ApiResponse> registerUser(RegistrationRequest request) {

        try {

            User user = setUserDetails(request, "register", null);

            System.out.println(userRepository.existsByEmail(user.getEmail()));

            user = userRepository.save(user);

            return ResponseEntity.ok(new ApiResponse("User saved successfully", user));

        } catch (Exception exception) {

            String errorMessage = "Failed to register user: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);


        }


    }

    private User setUserDetails(@Nullable RegistrationRequest registrationRequest, String type, @Nullable User request) {

        User user = new User();

        if (Objects.equals(type, "login")) {
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
        }else {
            user.setName(registrationRequest.getName());
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setPhone(registrationRequest.getPhone());
            user.setRole(registrationRequest.getRole());
        }

        return user;
    }

    public ResponseEntity<ApiResponse> loginUser(User request) {

        try {
            User user = setUserDetails(null, "login", request);

            String message = "user retrieved d";

            return ResponseEntity.ok(new ApiResponse(message, user));

        } catch (Exception exception) {

            String errorMessage = "Failed to login user: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}
