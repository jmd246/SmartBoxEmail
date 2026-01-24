package com.example;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import com.example.infrastructure.gmail.GmailClientFactory;
import com.google.api.services.gmail.Gmail;

public class GmailBuildTest {

    @Test
    public void testGmailClientCreation() throws GeneralSecurityException, IOException {
        GmailClientFactory factory = new GmailClientFactory();

        OAuth2AccessToken token = new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            "dummy-token",
            Instant.now(),
            Instant.now().plusSeconds(3600)
        );

        Gmail gmail = factory.create(token);

        assertNotNull(gmail);
    }
}
