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



    @GetMapping("/{parkingId}/entries")
    public List<EntrySummaryResponseDTO> listEntries(@PathVariable Long parkingId) {
        return entryService.listActiveEntries(parkingId);
    }

    @GetMapping("/{parkingId}/dashboard")
    public ParkingDashboardResponseDTO getDashboard(@PathVariable Long parkingId) {
        return parkingService.getDashboard(parkingId);
    }
}