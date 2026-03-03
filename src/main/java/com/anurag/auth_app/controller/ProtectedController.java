package com.anurag.auth_app.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/protected")
@CrossOrigin(origins = "http://localhost:3000")
public class ProtectedController {
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(Authentication
                                                  authentication) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to the protected dashboard!");
        response.put("user", authentication.getName());
        return ResponseEntity.ok(response);
    }
}