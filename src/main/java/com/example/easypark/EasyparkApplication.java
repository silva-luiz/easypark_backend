package com.example.easypark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EasyparkApplication {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));

        SpringApplication.run(EasyparkApplication.class, args);
    }
}