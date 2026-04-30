package com.example.easypark.controller;

import com.example.easypark.dto.EntrySummaryResponseDTO;
import com.example.easypark.service.EntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkings")
public class ParkingController {

    private final EntryService entryService;

    public ParkingController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping("/{parkingId}/entries")
    public List<EntrySummaryResponseDTO> listEntries(@PathVariable Long parkingId) {
        return entryService.listActiveEntries(parkingId);
    }
}