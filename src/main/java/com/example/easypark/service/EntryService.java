package com.example.easypark.service;

import com.example.easypark.dto.EntryRequestDTO;
import com.example.easypark.entity.Entry;
import com.example.easypark.entity.Vehicle;
import com.example.easypark.entity.EntryStatus;
import com.example.easypark.exception.BusinessException;
import com.example.easypark.repository.EntryRepository;
import com.example.easypark.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntryService {

    private final VehicleRepository vehicleRepository;
    private final EntryRepository entryRepository;

    public EntryService(VehicleRepository vehicleRepository, EntryRepository entryRepository) {
        this.vehicleRepository = vehicleRepository;
        this.entryRepository = entryRepository;
    }

    public Entry createEntry(EntryRequestDTO request) {

        // 1. Verificar se já existe entrada aberta
        entryRepository.findByVehiclePlateAndStatus(
                request.getPlate(),
                EntryStatus.OPEN
        ).ifPresent(entry -> {
            throw new BusinessException("Vehicle already inside the parking lot");
        });

        // 2. Buscar ou criar veículo
        Vehicle vehicle = vehicleRepository
                .findByPlate(request.getPlate())
                .orElseGet(() -> {
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setPlate(request.getPlate());
                    return vehicleRepository.save(newVehicle);
                });

        // 3. Criar entry
        Entry entry = new Entry();
        entry.setVehicle(vehicle);
        entry.setEntryTime(LocalDateTime.now());
        entry.setCustomerType(request.getCustomerType());
        entry.setStatus(EntryStatus.OPEN);

        return entryRepository.save(entry);
    }
}