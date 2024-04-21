package com.example.fortuneforge.config;

import com.example.fortuneforge.models.Token;
import com.example.fortuneforge.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);

        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken != null) {
            System.out.println("Token stored: " + storedToken);
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }else {
            System.out.println("token not found: " + token);
        }
    }
}
