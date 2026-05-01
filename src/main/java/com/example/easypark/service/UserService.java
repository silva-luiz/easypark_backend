package com.example.easypark.service;

import com.example.easypark.dto.CreateUserRequestDTO;
import com.example.easypark.entity.Parking;
import com.example.easypark.entity.User;
import com.example.easypark.repository.ParkingRepository;
import com.example.easypark.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ParkingRepository parkingRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(CreateUserRequestDTO request) {

        Parking parking = parkingRepository.findById(request.getParkingId())
                .orElseThrow(() -> new RuntimeException("Parking not found"));

        User user = new User();
        user.setEmail(request.getEmail());

        // 🔐 AQUI É O PONTO MAIS IMPORTANTE
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setParking(parking);

        userRepository.save(user);
    }
}