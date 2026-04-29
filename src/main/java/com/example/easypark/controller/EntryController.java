package com.example.easypark.controller;

import com.example.easypark.dto.EntryRequestDTO;
import com.example.easypark.dto.EntryResponseDTO;
import com.example.easypark.entity.Entry;
import com.example.easypark.service.EntryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping
    public EntryResponseDTO create(@RequestBody EntryRequestDTO request) {
        return entryService.createEntry(request);
    }
}