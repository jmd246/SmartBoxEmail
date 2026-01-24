package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dto.InboxRow;
//integrate with mockito and junit5
public class InboxRowTest {
        private InboxRow inboxRow;
    @Test
    public void testInboxRowCreation() {
        String id = "12345";
        String from = "test@example.com";
        String subject = "Test Subject";
        Long receivedAt = System.currentTimeMillis();
        inboxRow = new InboxRow(id, from, subject, receivedAt);
        assertEquals(id, inboxRow.getId());
        assertEquals(from, inboxRow.getFrom()); 
        assertEquals(subject, inboxRow.getSubject());
        assertEquals(receivedAt, inboxRow.getReceivedAt()); 
    }


}