package com.example.easypark.controller;

import com.example.easypark.dto.ExitRequestDTO;
import com.example.easypark.dto.ExitResponseDTO;
import com.example.easypark.entity.Exit;
import com.example.easypark.service.ExitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exits")
public class ExitController {

    private final ExitService exitService;

    public ExitController(ExitService exitService) {
        this.exitService = exitService;
    }

    @PostMapping
    public Exit registerExit(@RequestBody ExitRequestDTO request) {
        return exitService.registerExit(request.getPlate());
    }
}