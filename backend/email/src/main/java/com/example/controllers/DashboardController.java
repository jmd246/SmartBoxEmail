
package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
