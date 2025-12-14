package com.example.services;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class GoogleService {
        private final Environment env;

    public GoogleService(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void check() {
        System.out.println("Google Client ID: " + env.getProperty("google.client-id"));
        System.out.println("Client Secret is set: " + (env.getProperty("google.client-secret") != null));
    }

}
