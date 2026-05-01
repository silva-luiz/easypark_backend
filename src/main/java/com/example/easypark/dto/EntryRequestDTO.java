package com.example.easypark.dto;

import com.example.easypark.entity.CustomerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryRequestDTO {
    private String plate;
    private CustomerType customerType;
}