package com.anurag.auth_app.dto;
import lombok.Data;
@Data
public class LoginRequest {
    private String username;
    private String password;
}