package com.example.api.gmail;

public record GmailMessage(
    String id,
    String threadId,
    GmailMessagePart payload,
    String snippet
) {

}
