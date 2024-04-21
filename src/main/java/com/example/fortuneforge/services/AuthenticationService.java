package com.example.fortuneforge.services;

import com.example.fortuneforge.models.Token;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.TokenRepository;
import com.example.fortuneforge.repositories.UserRepository;
import com.example.fortuneforge.requests.authentication.LoginRequest;
import com.example.fortuneforge.requests.authentication.RegistrationRequest;
import com.example.fortuneforge.config.ApiResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public ResponseEntity<ApiResponse> registerUser(RegistrationRequest request) {

        try {

            User user = setUserDetails(request);

            System.out.println(userRepository.existsByEmail(user.getEmail()));

            user = userRepository.save(user);

            return ResponseEntity.ok(new ApiResponse("User saved successfully", user, null));

        } catch (Exception exception) {

            String errorMessage = "Failed to register user: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);


        }


    }

    private User setUserDetails(@Nullable RegistrationRequest registrationRequest) {

        User user = new User();

        assert registrationRequest != null;
        user.setName(registrationRequest.getName());

        user.setEmail(registrationRequest.getEmail());

        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        user.setPhone(registrationRequest.getPhone());

        user.setRole(registrationRequest.getRole());


        return user;
    }

    public ResponseEntity<ApiResponse> loginUser(LoginRequest request) {

        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found: " + request.getEmail()));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            String jwt = jwtService.generateToken(user);

            revokeExistingTokens(user);

            saveUserToken(jwt, user);

            return ResponseEntity.ok(new ApiResponse("User saved successfully: " + jwt, user, jwt));

        } catch (Exception exception) {

            String errorMessage = "Failed to login user: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);

        tokenRepository.save(token);
    }

    private void revokeExistingTokens(User user) {
        List<Token> validTokens = tokenRepository.findAllTokenByUser(user.getId());

        if (!validTokens.isEmpty()) {
            validTokens.forEach(token -> {
                token.setLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokens);
    }
}
