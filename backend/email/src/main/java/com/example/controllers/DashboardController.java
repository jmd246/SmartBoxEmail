
package com.example.controllers;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Owner
 */
@RestController
public class DashboardController{
   @GetMapping("/dashboard")
   public Map<String, Object> dashboard(@AuthenticationPrincipal OAuth2User user) {
       return Map.of(
        "name", user.getAttribute("name"),
        "email",user.getAttribute("email"),
        "attributes", user.getAttributes()
        );
   }

}
