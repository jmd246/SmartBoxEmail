package com.example.services;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class GoogleOAuthService {
   // This class can be expanded to include methods for handling Google OAuth specific logic
   private final OAuth2AuthorizedClientService authorizedClientService;

   public GoogleOAuthService(OAuth2AuthorizedClientService authorizedClientService) {
       this.authorizedClientService = authorizedClientService;
   }
   public OAuth2AccessToken getAccessToken(OAuth2AuthenticationToken authentication) {
       // Placeholder method to demonstrate potential functionality
       //token retrieval logic would go here
       OAuth2AuthenticationToken token =  (OAuth2AuthenticationToken) authentication;

       OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
           token.getAuthorizedClientRegistrationId(), 
           token.getName() // This should be replaced with the actual principal name
       );   
       return client.getAccessToken();
   }    
}
