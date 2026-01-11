package com.example.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.InboxRow;

import com.example.services.EmailService;
import com.example.services.GoogleOAuthService;

@RestController
public class EmailController {
private final EmailService emailService;
private final GoogleOAuthService googleOAuthService;

    public EmailController(EmailService emailService, GoogleOAuthService googleOAuthService) {
        this.emailService = emailService;
        this.googleOAuthService =  googleOAuthService;
    }
    @GetMapping("/email/inbox")
    public List<InboxRow> getInboxEmails(OAuth2AuthenticationToken authentication) throws GeneralSecurityException {
        OAuth2AccessToken accessToken = googleOAuthService.getAccessToken(authentication);
        try {
            return emailService.fetchInbox(accessToken);
        } catch (IOException | GeneralSecurityException e) {
            throw new GeneralSecurityException("Failed to fetch emails", e);
        }   
    }
 
  
}
