package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.gmail.GmailMessage;
import com.example.services.GmailService;
import com.example.services.GoogleOAuthService;

import reactor.core.publisher.Flux;
@RestController
public class GmailController {
private final GmailService gmailService;
private final GoogleOAuthService googleOAuthService;

    public GmailController(GmailService gmailService, GoogleOAuthService googleOAuthService) {
        this.gmailService = gmailService;
        this.googleOAuthService =  googleOAuthService;
    }
    @GetMapping("/gmail/labels")
    public String getGmailLabels(org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken    authentication) {
        String accessToken = googleOAuthService.getAccessToken(authentication);
        return gmailService.getLabels(accessToken);
    }
      @GetMapping("/gmail/emails")
    public Flux<GmailMessage> getGmailEmails(org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken    authentication) {
        String accessToken = googleOAuthService.getAccessToken(authentication);
        return gmailService.fetchEmails(accessToken);
    }     
   
}
