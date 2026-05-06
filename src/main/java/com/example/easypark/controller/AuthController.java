package com.example.easypark.controller;

import com.example.easypark.dto.AuthResponseDTO;
import com.example.easypark.dto.LoginRequestDTO;
import com.example.easypark.dto.LoginResponseDTO;
import com.example.easypark.dto.RegisterRequestDTO;
import com.example.easypark.entity.User;
import com.example.easypark.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {

        String token = authService.login(request);

        return new LoginResponseDTO(token);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {

        return ResponseEntity.ok(authService.register(request));
    }
}