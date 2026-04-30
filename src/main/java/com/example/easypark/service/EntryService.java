package com.example.easypark.service;

import com.example.easypark.dto.EntryRequestDTO;
import com.example.easypark.dto.EntryResponseDTO;
import com.example.easypark.dto.EntrySummaryResponseDTO;
import com.example.easypark.entity.Entry;
import com.example.easypark.entity.Parking;
import com.example.easypark.entity.Vehicle;
import com.example.easypark.entity.EntryStatus;
import com.example.easypark.exception.BusinessException;
import com.example.easypark.repository.EntryRepository;
import com.example.easypark.repository.ParkingRepository;
import com.example.easypark.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class EntryService {

    private final VehicleRepository vehicleRepository;
    private final EntryRepository entryRepository;
    private final ParkingRepository parkingRepository;

    public EntryService(VehicleRepository vehicleRepository, EntryRepository entryRepository, ParkingRepository parkingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.entryRepository = entryRepository;
        this.parkingRepository = parkingRepository;
    }

    public EntryResponseDTO createEntry(EntryRequestDTO request) {

        // 1. Check if there is already an open entry
        entryRepository.findByVehiclePlateAndStatus(
                request.getPlate(),
                EntryStatus.OPEN
        ).ifPresent(entry -> {
            throw new BusinessException("Vehicle already inside the parking lot");
        });

        // 2. Find parking lot
        Parking parking = parkingRepository.findById(request.getParkingId())
                .orElseThrow(() -> new BusinessException("Parking not found"));

        // 3. Spots Control
        long occupiedSpots = entryRepository.countByParking_IdAndStatus(
                parking.getId(),
                EntryStatus.OPEN
        );

        if (occupiedSpots >= parking.getCapacity()) {
            throw new BusinessException("Parking is full");
        }


        // 4. Find or create vehicle
        Vehicle vehicle = vehicleRepository
                .findByPlate(request.getPlate())
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setPlate(request.getPlate());
                    return vehicleRepository.save(v);
                });

        // 5. Create entry
        Entry entry = new Entry();
        entry.setVehicle(vehicle);
        entry.setParking(parking);
        entry.setEntryTime(LocalDateTime.now());
        entry.setCustomerType(request.getCustomerType());
        entry.setStatus(EntryStatus.OPEN);

        Entry saved = entryRepository.save(entry);

        return new EntryResponseDTO(
                saved.getId(),
                saved.getVehicle().getPlate(),
                saved.getStatus().toString(),
                saved.getEntryTime()
        );
    }

    public List<EntrySummaryResponseDTO> listActiveEntries(Long parkingId) {

        List<Entry> entries = entryRepository
                .findByParking_IdAndStatus(parkingId, EntryStatus.OPEN);

        return entries.stream()
                .map(e -> {

                    Duration duration = Duration.between(
                            e.getEntryTime(),
                            LocalDateTime.now()
                    );

                    String formattedDuration = formatDuration(duration);

                    return new EntrySummaryResponseDTO(
                            e.getVehicle().getPlate(),
                            e.getEntryTime(),
                            formattedDuration
                    );
                })
                .toList();
    }

    private String formatDuration(Duration duration) {

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return hours + "h " + minutes + "min";
    }
}
