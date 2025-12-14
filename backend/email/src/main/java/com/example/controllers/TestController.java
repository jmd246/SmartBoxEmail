package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return """
            <html>
              <body>
                <a href="/oauth2/authorization/google">
                    Login with Google
                </a>
              </body>
            </html>
        """;
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Hello, World!";
    }
}
