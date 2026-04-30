package com.example.easypark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EntrySummaryResponseDTO {

    private String plate;
    private LocalDateTime entryTime;
    private String duration;
}