package com.example.fortuneforge.services;

import com.example.fortuneforge.models.User;
import com.example.fortuneforge.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    public ResponseEntity<ApiResponse> registerUser(User request) {

        try {

            User user = setUserDetails(request, "register");

            System.out.println(user);

            String message = "user retrieved d";

            return ResponseEntity.ok(new ApiResponse(message, user));

        } catch (Exception exception) {

            String errorMessage = "Failed to register user: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);


        }


    }

    private static User setUserDetails(User request, String type) {

        User user = new User();

        if (Objects.equals(type, "login")) {
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
        }else {
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setPhone(request.getPhone());
            user.setRole(request.getRole());
        }

        return user;
    }

    public ResponseEntity<ApiResponse> loginUser(User request) {

        try {
            User user = setUserDetails(request, "login");

            String message = "user retrieved d";

            return ResponseEntity.ok(new ApiResponse(message, user));

        } catch (Exception exception) {

            String errorMessage = "Failed to login user: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}
