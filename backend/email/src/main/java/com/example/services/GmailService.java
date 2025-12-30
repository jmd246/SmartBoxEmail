package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.api.gmail.GmailMessage;
import com.example.api.gmail.MessageIdList;
import com.example.api.gmail.MessagePartHeader;
import com.example.dto.InboxRow;

import reactor.core.publisher.Flux;

@Service
public class GmailService {

  private final WebClient client;
  public GmailService(WebClient.Builder webClientBuilder) {
      this.client = webClientBuilder.build();
}
  public static String headerValue(List<MessagePartHeader> headers,String name){
    return headers.stream()
        .filter(h -> h.name().equalsIgnoreCase(name))
        .map(MessagePartHeader::value)
        .findFirst()
        .orElse(null);
    }
  public String getLabels(String accessToken) {
      return client.get()
          .uri("https://www.googleapis.com/gmail/v1/users/me/labels")
          .headers(headers -> headers.setBearerAuth(accessToken))
          .retrieve()
          .bodyToMono(String.class)
          .block();
  }

    public Flux<GmailMessage> fetchEmail(String messageId, String accessToken) {
        return client.get()
            .uri("https://www.googleapis.com/gmail/v1/users/me/messages/{id}?format=metadata", messageId)
            .headers(headers -> headers.setBearerAuth(accessToken))
            .retrieve()
            .bodyToMono(GmailMessage.class)
            .flux();
    }
  public Flux<GmailMessage> fetchEmails(String accessToken) {
      return client.get()
          .uri("https://www.googleapis.com/gmail/v1/users/me/messages")
          .headers(headers -> headers.setBearerAuth(accessToken))
          .retrieve()
          .bodyToMono(MessageIdList.class)
          .flatMapMany(ids -> Flux.fromIterable(ids.messages()))
          .flatMap(msgId -> fetchEmail(msgId.id(),accessToken));
  }
  public InboxRow getEmailDetails(GmailMessage message) {
        String from = headerValue(message.payload().headers(), "From");
        String subject = headerValue(message.payload().headers(), "Subject");
        String snippet = message.snippet();
        // Placeholder for receivedAt, actual implementation would parse the date header
        return new InboxRow(message.id(), from, subject, null, snippet);    
  }
}