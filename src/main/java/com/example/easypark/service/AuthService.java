package com.example.easypark.service;

import com.example.easypark.dto.AuthResponseDTO;
import com.example.easypark.dto.LoginRequestDTO;
import com.example.easypark.dto.RegisterRequestDTO;
import com.example.easypark.entity.User;
import com.example.easypark.exception.BusinessException;
import com.example.easypark.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO request) {
        return "token";
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setParking(null);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token, false);
    }
}