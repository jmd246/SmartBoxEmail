package com.example.infrastructure.gmail;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;

@Service
public class GmailClientFactory {
   public Gmail create(OAuth2AccessToken accessToken) throws GeneralSecurityException, IOException {
       return new Gmail.Builder(
           GoogleNetHttpTransport.newTrustedTransport(),
           GsonFactory.getDefaultInstance(),
              request -> request.getHeaders().setAuthorization("Bearer " + accessToken.getTokenValue()
           )                
       ).setApplicationName("smartbox").build();
   }    
}
