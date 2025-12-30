package com.example.api.gmail;
import java.util.List;

public record GmailMessagePart(List<MessagePartHeader> headers) {
}
