package com.example.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.example.dto.InboxResponse;
import com.example.dto.InboxRow;
import com.example.infrastructure.gmail.GmailClientFactory;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse; 
import com.google.api.services.gmail.model.Message;





@Cacheable(value = "inbox",key = "#authentication.tokenValue", unless="#result == null")

@Service
public class EmailService {
 
  private final GmailClientFactory gmailClientFactory;

  //private final WebClient client;
  public EmailService(GmailClientFactory gmailClientFactory) {
      this.gmailClientFactory = gmailClientFactory;
  }
  public InboxResponse fetchInbox(OAuth2AccessToken authentication) throws GeneralSecurityException, IOException {
     //log out access token info
     System.out.println("Fetching inbox with access token: " + authentication.getTokenValue());
     Logger logger = LoggerFactory.getLogger(EmailService.class);
        logger.info("Fetching inbox with access token: " + authentication.getTokenValue());
        
     Gmail gmail = gmailClientFactory.create(authentication);
      //get list of messages in inbox first 20 unread
      ListMessagesResponse messagesResponse = gmail.users().messages()
      .list("me")
      .setMaxResults(20L)
      .setFields("messages(id),nextPageToken")
      .setLabelIds(List.of("INBOX"))
      .setQ("is:unread")
      .execute();
      //need a thread safe list to hold inbox rows

      List<InboxRow> inboxRows = Collections.synchronizedList(new ArrayList<>());
      
      String pageToken = messagesResponse.getNextPageToken();
      //if no messages in response return empty inbox
        if(messagesResponse.getMessages() == null || messagesResponse.getMessages().isEmpty()) {
            return new InboxResponse(inboxRows, pageToken);
        }
      //gmail batch request to get message details
      BatchRequest batch = gmail.batch();

      for(Message message : messagesResponse.getMessages()) {
          //prepare each message request
          gmail.users().messages()
          .get("me", message.getId())
          .setFormat("metadata")
          .setMetadataHeaders(List.of("From","Subject"))
          .setFields("id,internalDate,payload(headers)")
          .queue(batch, new JsonBatchCallback<Message>(){
                @Override
                public void onSuccess(Message fullMessage, com.google.api.client.http.HttpHeaders responseHeaders) throws IOException {
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
                    inboxRows.add(new InboxRow(fullMessage.getId(), from, subject, receivedAt));
                }
    
                @Override
                public void onFailure(com.google.api.client.googleapis.json.GoogleJsonError e, com.google.api.client.http.HttpHeaders responseHeaders) throws IOException {
                    //handle failure
                    System.err.println("Error fetching message: " + e.getMessage());
                }

          });
      }

      //execute batch request
      batch.execute();   
      //batch does not guarantee order so we can sort by received date descending
      inboxRows.sort((a, b) -> b.getReceivedAt().compareTo(a.getReceivedAt()));

      return new InboxResponse(inboxRows, pageToken);
    }


    //search emails in inbox by query
    public InboxResponse searchEmails(OAuth2AccessToken authentication, String query) throws GeneralSecurityException, IOException {
        //build gmail client with access token
        Gmail gmail = gmailClientFactory.create(authentication);
        //get list of messages in inbox matching query
        ListMessagesResponse messagesResponse = gmail.users().messages()
        .list("me")
        .setQ(query)
        .execute(); 
    
        List<InboxRow> inboxRows = Collections.synchronizedList(new ArrayList<>());
        String pageToken = messagesResponse.getNextPageToken();
        //if no messages in response return empty inbox
        if(messagesResponse.getMessages() == null || messagesResponse.getMessages().isEmpty()) {
            return new InboxResponse(inboxRows, pageToken);
        }
        //gmail batch request to get message details
        BatchRequest batch = gmail.batch(); 
        for(Message message : messagesResponse.getMessages()) {
            //prepare each message request
            gmail.users().messages()
            .get("me", message.getId())
            .setFormat("metadata")
            .setMetadataHeaders(List.of("From","Subject"))
            .setFields("id,internalDate,payload(headers)")
            .queue(batch, new JsonBatchCallback<Message>(){
                  @Override
                  public void onSuccess(Message fullMessage, com.google.api.client.http.HttpHeaders responseHeaders) throws IOException {
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
                      inboxRows.add(new InboxRow(fullMessage.getId(), from, subject, receivedAt));
                  }
  
                  @Override
                  public void onFailure(com.google.api.client.googleapis.json.GoogleJsonError e, com.google.api.client.http.HttpHeaders responseHeaders) throws IOException {
                      //handle failure
                      System.err.println("Error fetching message: " + e.getMessage());
                  }

            });
        }
    
        //execute batch request
        batch.execute();   
        //batch does not guarantee order so we can sort by received date descending
        inboxRows.sort((a, b) -> b.getReceivedAt().compareTo(a.getReceivedAt()));

        return new InboxResponse(inboxRows, pageToken);
    }
}