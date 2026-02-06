package com.example.infrastructure;
import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class OAuth2LoginSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        // Optional: force session creation
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();

        String redirectUri = "http://localhost:9000/login-success?sid=" + sessionId;


        getRedirectStrategy().sendRedirect(
                request,
                        response,
                redirectUri
        );
    }
}