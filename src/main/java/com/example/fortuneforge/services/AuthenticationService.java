package com.example.fortuneforge.services;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.config.CatchErrorResponses;
import com.example.fortuneforge.models.PasswordResets;
import com.example.fortuneforge.models.Role;
import com.example.fortuneforge.models.Token;
import com.example.fortuneforge.models.User;
import com.example.fortuneforge.repositories.PasswordResetRepository;
import com.example.fortuneforge.repositories.TokenRepository;
import com.example.fortuneforge.repositories.UserRepository;
import com.example.fortuneforge.requests.authentication.LoginRequest;
import com.example.fortuneforge.requests.authentication.PasswordResetRequest;
import com.example.fortuneforge.requests.authentication.RegistrationRequest;
import com.example.fortuneforge.service_impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserDetailServiceImpl userDetailService;

    public ResponseEntity<ApiResponse> registerUser(RegistrationRequest request) {

        try {

            User user = setUserDetails(request);

            System.out.println(userRepository.existsByEmail(user.getEmail()));

            user = userRepository.save(user);

            return ResponseEntity.ok(new ApiResponse("User saved successfully", user, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Failed to register user: ", exception);

        }

    }

    public ResponseEntity<ApiResponse> loginUser(LoginRequest request) {

        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            String jwt = jwtService.generateToken(user);

            revokeExistingTokens(user);

            saveUserToken(jwt, user);

            return ResponseEntity.ok(new ApiResponse("User saved successfully: ", user, jwt));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Failed to login user: ", exception);
        }

    }

    @Transactional
    public ResponseEntity<ApiResponse> forgotPassword(User user) {

        try {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {

                Optional<User> selectedUser = userRepository.findByEmail(user.getEmail());

                if (selectedUser.isPresent()) {

                    User userWithPasswordResets = selectedUser.get();

                    deletePasswordResets(userWithPasswordResets);

                    PasswordResets passwordResets = new PasswordResets();

                    passwordResets.setUser(selectedUser.get());
                    passwordResets.setToken(generateUUID());
                    passwordResets.setExpiryDate(LocalDateTime.now().plusHours(1));
                    passwordResets.setUsed(false);

                    passwordResetRepository.save(passwordResets);

                    Context context = new Context();

                    context.setVariable("name", selectedUser.get().getName());

                    context.setVariable("url", "http://localhost:4200/auth/password/reset/" + passwordResets.getToken());

                    emailService.sendEmail(user.getEmail(), "Password Reset Email", "password-reset", context);

                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Email sent successfully. Kindly view the email to continue the password reset process", user.getEmail(), null));
                }

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to send email. User not found", null, null));

            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to send email.", null, null));

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Failed to send password reset email: ", exception);
        }


    }

    public ResponseEntity<ApiResponse> passwordReset(PasswordResetRequest request) {

        try {

            PasswordResets passwordResets = passwordResetRepository.findPasswordResetsByToken(request.getToken())
                    .orElseThrow(() -> new RuntimeException("Password reset token does not exist. Kindly generate a new one."));

            if (passwordResets.getExpiryDate().isAfter(LocalDateTime.now()) && !passwordResets.isUsed()) {

                resetUserPassword(request, passwordResets);

                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Password Reset Successful", null, null));

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Password reset token expired. Kindly generate a new one.", null, null));
            }

        } catch (Exception exception) {

            return CatchErrorResponses.catchErrors("Failed to send password reset email: ", exception);
        }

    }

    private void resetUserPassword(PasswordResetRequest request, PasswordResets passwordResets) {
        passwordResets.setUsed(true);

        passwordResetRepository.save(passwordResets);

        User user = passwordResets.getUser();

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
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

    @Transactional
    public void deletePasswordResets(User user) {
        passwordResetRepository.deleteAllByUser(user);
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

    public User retrieveUserFromToken(String token) {

        String requestToken = token.substring(7);

        String username = jwtService.extractUsername(requestToken);

        if (username != null) {

            return (User) userDetailService.loadUserByUsername(username);
        }

        return null;

    }

    public User validateUserFromToken(String token) throws Exception {
        User user = retrieveUserFromToken(token);

        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }
}
