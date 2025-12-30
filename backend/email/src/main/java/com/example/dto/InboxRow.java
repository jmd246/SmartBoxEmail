package com.example.dto;

import java.time.Instant;

public record InboxRow(
    String id,
    String from,
    String subject,
    Instant receivedAt,
    String snippet
) {}
