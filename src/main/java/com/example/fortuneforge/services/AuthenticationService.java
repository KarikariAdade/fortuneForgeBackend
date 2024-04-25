package com.example.fortuneforge.services;

import com.example.fortuneforge.models.PasswordResets;
import com.example.fortuneforge.models.Role;
import com.example.fortuneforge.models.Token;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.PasswordResetRepository;
import com.example.fortuneforge.repositories.TokenRepository;
import com.example.fortuneforge.repositories.UserRepository;
import com.example.fortuneforge.requests.authentication.LoginRequest;
import com.example.fortuneforge.requests.authentication.RegistrationRequest;
import com.example.fortuneforge.config.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthEmailService emailService;
    private final PasswordResetRepository passwordResetRepository;

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

    private User setUserDetails(RegistrationRequest registrationRequest) {

        User user = new User();

        assert registrationRequest != null;
        user.setName(registrationRequest.getName());

        user.setEmail(registrationRequest.getEmail());

        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        user.setPhone(registrationRequest.getPhone());

        user.setRole(Role.valueOf(registrationRequest.getRole()));

        return user;
    }

    public ResponseEntity<ApiResponse> loginUser(LoginRequest request) {

        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

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

    public ResponseEntity<ApiResponse> forgotPassword (User user) {

        try {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {

                Optional<User> selectedUser = userRepository.findByEmail(user.getEmail());

                if (selectedUser.isPresent()) {

                    PasswordResets passwordResets = new PasswordResets();

                    passwordResets.setUser(selectedUser.get());
                    passwordResets.setToken(generateUUID());
                    passwordResets.setExpiryDate(LocalDateTime.now().plusHours(1));
                    passwordResets.setUsed(false);

                    passwordResetRepository.save(passwordResets);

                    Context context = new Context();

                    context.setVariable("name", selectedUser.get().getName());

                    context.setVariable("url", "localhost:4200/password/reset/" + passwordResets.getToken());

                    String htmlContent = "<html><body><h1>Hello!</h1><p>This is an HTML email.</p></body></html>";

                    emailService.sendEmail(user.getEmail(), "Password Reset Email", "password-reset", context);

                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Email sent successfully", user.getEmail(), null));
                }

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to send email. User not found", null, null));

            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to send email.", null, null));

        } catch (Exception exception) {

            String errorMessage = "Failed to send password reset email: " + exception.getMessage();

            ApiResponse errorResponse = new ApiResponse(errorMessage, null, null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }


    }

    public ResponseEntity<ApiResponse> passwordReset(String token) {
        return null;
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

            validTokens.forEach(token -> token.setLoggedOut(true));
        }

        tokenRepository.saveAll(validTokens);
    }

    private String generateUUID() {

        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
