package com.example.dto;

import java.util.List;

public class InboxResponse {
    final List<InboxRow> emails;
    final String nextPageToken;

    //no args constructor for JSON deserialization
    public InboxResponse() {
        this.emails = null;
        this.nextPageToken = null;
    }

    public InboxResponse(List<InboxRow> emails, String nextPageToken) {
        this.emails = emails;
        this.nextPageToken = nextPageToken;
    }
    public List<InboxRow> getEmails() {
        return emails;
    }
    public String getNextPageToken() {
        return nextPageToken;
    }
}
