package com.example.kantoronline.services.logout;

import com.example.kantoronline.entities.Token;
import com.example.kantoronline.exceptions.ThisTokenIsNotActiveException;
import com.example.kantoronline.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Token token = tokenRepository.findByToken(jwt).orElseThrow(ThisTokenIsNotActiveException::new);
        tokenRepository.delete(token);
    }
}
