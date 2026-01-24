package com.example.dto;


public class InboxRow{
    
    private final String id;
    private final String from;
    private final String subject;
    private final Long receivedAt;
    

    public InboxRow(String id, String from, String subject, Long receivedAt) {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.receivedAt = receivedAt;
    }

    public String getId() {
        return id;
    }
    public String getFrom() {
        return from;
    }
    public String getSubject() {
        return subject;
    }
    

    public Long getReceivedAt() {
        return receivedAt;
    }   

}