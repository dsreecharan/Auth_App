package com.anurag.auth_app.service;
import com.anurag.auth_app.dto.JwtResponse;
import com.anurag.auth_app.dto.LoginRequest;
import com.anurag.auth_app.dto.MessageResponse;
import com.anurag.auth_app.dto.SignupRequest;
import com.anurag.auth_app.entity.User;
import com.anurag.auth_app.repository.UserRepository;
import com.anurag.auth_app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
        String token =
                jwtUtil.generateToken(loginRequest.getUsername());
        User user =
                userRepository.findByUsername(loginRequest.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));
        return new JwtResponse(token, user.getUsername(),
                user.getEmail());
    }
    public MessageResponse signup(SignupRequest signupRequest) {
        if
        (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()))
        ;
        user.setEmail(signupRequest.getEmail());
        user.setRole("USER");
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");
    }
} 