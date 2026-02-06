
package com.example.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Owner
 */
@RestController
public class DashboardController{
   @GetMapping("/")
   public String defaultRoute(){
      return "Need to login";
   }

@GetMapping("/api/auth/status")
public ResponseEntity<?> authStatus(HttpServletRequest request) {
    return ResponseEntity.ok(
        Map.of(
            "authenticated", request.getUserPrincipal() != null
        )
    );
}

}