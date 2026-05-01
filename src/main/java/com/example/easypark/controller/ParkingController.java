package com.example.easypark.controller;

import com.example.easypark.dto.EntrySummaryResponseDTO;
import com.example.easypark.dto.ParkingDashboardResponseDTO;
import com.example.easypark.service.EntryService;
import com.example.easypark.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkings")
public class ParkingController {

    private final EntryService entryService;
    private final ParkingService parkingService;

    public ParkingController(EntryService entryService, ParkingService parkingService) {
        this.entryService = entryService;
        this.parkingService = parkingService;
    }

    @GetMapping("/entries")
    public List<EntrySummaryResponseDTO> listEntries() {
        return entryService.listActiveEntries();
    }

    @GetMapping("/dashboard")
    public ParkingDashboardResponseDTO get() {
        return parkingService.getDashboard();
    }
}