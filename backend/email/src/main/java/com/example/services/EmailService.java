package com.example.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.example.dto.InboxRow;

import com.example.infrastructure.gmail.GmailClientFactory;

import com.google.api.services.gmail.model.Message;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse; 

@Service
public class EmailService {
 
  private final GmailClientFactory gmailClientFactory;

  //private final WebClient client;
  public EmailService(GmailClientFactory gmailClientFactory) {
      this.gmailClientFactory = gmailClientFactory;
  }
  public List<InboxRow> fetchInbox(OAuth2AccessToken authentication) throws GeneralSecurityException, IOException {
      Gmail gmail = gmailClientFactory.create(authentication);
      
      ListMessagesResponse messagesResponse = gmail.users().messages().list("me").setMaxResults(10L).execute();
     List<InboxRow> inboxRows = new ArrayList<>();
      for (Message message : messagesResponse.getMessages()) {
          Message fullMessage = gmail.users().messages().get("me", message.getId()).execute();
          
          String from = fullMessage.getPayload().getHeaders().stream()
              .filter(header -> "From".equalsIgnoreCase(header.getName()))
              .findFirst()
              .map(header -> header.getValue())
              .orElse("Unknown Sender");

          String subject = fullMessage.getPayload().getHeaders().stream()
              .filter(header -> "Subject".equalsIgnoreCase(header.getName()))
              .findFirst()
              .map(header -> header.getValue())
              .orElse("No Subject");
          Long receivedAt = fullMessage.getInternalDate();
          String snippet = fullMessage.getSnippet();          
          inboxRows.add(new InboxRow(message.getId(), from, subject,receivedAt,snippet));
      }
      return inboxRows; 
  }
}