package com.example.easypark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParkingDashboardResponseDTO {

    private String parkingName;
    private int totalSpots;
    private long occupiedSpots;
    private long availableSpots;
    private double occupancyRate;
}