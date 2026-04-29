package com.example.easypark.dto;

import java.time.LocalDateTime;

public class EntryResponseDTO {

    private Long id;
    private String plate;
    private String status;
    private LocalDateTime entryTime;

    public EntryResponseDTO(Long id, String plate, String status, LocalDateTime entryTime) {
        this.id = id;
        this.plate = plate;
        this.status = status;
        this.entryTime = entryTime;
    }

    public Long getId() { return id; }
    public String getPlate() { return plate; }
    public String getStatus() { return status; }
    public LocalDateTime getEntryTime() { return entryTime; }
}