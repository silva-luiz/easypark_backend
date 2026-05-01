package com.example.easypark.controller;

import com.example.easypark.dto.LoginRequestDTO;
import com.example.easypark.dto.LoginResponseDTO;
import com.example.easypark.service.AuthService;
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
}