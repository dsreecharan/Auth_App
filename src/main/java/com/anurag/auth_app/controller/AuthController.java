package com.anurag.auth_app.controller;
import com.anurag.auth_app.dto.JwtResponse;
import com.anurag.auth_app.dto.LoginRequest;
import com.anurag.auth_app.dto.MessageResponse;
import com.anurag.auth_app.dto.SignupRequest;
import com.anurag.auth_app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        try {
            JwtResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid credentials!"));
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            MessageResponse response = authService.signup(signupRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
