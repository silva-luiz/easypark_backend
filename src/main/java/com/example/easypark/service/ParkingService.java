package com.example.easypark.service;

import com.example.easypark.dto.ParkingDashboardResponseDTO;
import com.example.easypark.entity.EntryStatus;
import com.example.easypark.entity.Parking;
import com.example.easypark.exception.BusinessException;
import com.example.easypark.repository.EntryRepository;
import com.example.easypark.repository.ParkingRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final EntryRepository entryRepository;

    public ParkingService(ParkingRepository parkingRepository, EntryRepository entryRepository) {
        this.parkingRepository = parkingRepository;
        this.entryRepository = entryRepository;
    }

    public ParkingDashboardResponseDTO getDashboard(Long parkingId) {

        // 1. Find parking by ID
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new BusinessException("Parking not found"));

        // 2. Count occupied spots (vehicles with OPEN status)
        long occupied = entryRepository.countByParking_IdAndStatus(
                parkingId,
                EntryStatus.OPEN
        );

        // 3. Calculate available spots
        long available = parking.getCapacity() - occupied;

        // 4. Calculate occupancy rate (%)
        double occupancyRate = (parking.getCapacity() == 0)
                ? 0
                : (occupied * 100.0) / parking.getCapacity();

        // 5. Round occupancy rate
        occupancyRate = Math.round(occupancyRate * 100.0) / 100.0;

        return new ParkingDashboardResponseDTO(
                parking.getName(),
                parking.getCapacity(),
                occupied,
                available,
                occupancyRate
        );
    }
}