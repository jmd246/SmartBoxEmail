package com.example.dto;


public record InboxRow(
    String id,
    String from,
    String subject,
    Long receivedAt,
    String snippet
) {}
