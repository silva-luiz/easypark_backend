package com.example.easypark.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExitResponseDTO {

    private Long id;
    private String plate;
    private LocalDateTime exitTime;
    private BigDecimal totalAmount;
    private boolean paid;

    public ExitResponseDTO(Long id, String plate, LocalDateTime exitTime, BigDecimal totalAmount, boolean paid) {
        this.id = id;
        this.plate = plate;
        this.exitTime = exitTime;
        this.totalAmount = totalAmount;
        this.paid = paid;
    }

    public Long getId() { return id; }
    public String getPlate() { return plate; }
    public LocalDateTime getExitTime() { return exitTime; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public boolean isPaid() { return paid; }
}