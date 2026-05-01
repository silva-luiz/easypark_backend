package com.example.easypark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDTO {
    private String email;
    private String password;
    private Long parkingId;
}