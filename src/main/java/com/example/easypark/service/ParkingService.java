package com.example.easypark.service;

import com.example.easypark.dto.ParkingDashboardResponseDTO;
import com.example.easypark.entity.EntryStatus;
import com.example.easypark.entity.Parking;
import com.example.easypark.entity.User;
import com.example.easypark.exception.BusinessException;
import com.example.easypark.repository.EntryRepository;
import com.example.easypark.repository.ParkingRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final EntryRepository entryRepository;

    public ParkingService(ParkingRepository parkingRepository, EntryRepository entryRepository) {
        this.parkingRepository = parkingRepository;
        this.entryRepository = entryRepository;
    }

    public ParkingDashboardResponseDTO getDashboard() {

        // 🔐 1. GET LOGGED USER
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Parking parking = user.getParking();

        // 2. COUNT OCCUPIED SPOTS
        long occupied = entryRepository.countByParking_IdAndStatus(
                parking.getId(),
                EntryStatus.OPEN
        );

        // 3. CALCULATE AVAILABLE SPOTS
        long available = parking.getCapacity() - occupied;

        // 4. CALCULATE OCCUPANCY RATE (%)
        double occupancyRate = (parking.getCapacity() == 0)
                ? 0
                : (occupied * 100.0) / parking.getCapacity();

        occupancyRate = Math.round(occupancyRate);

        return new ParkingDashboardResponseDTO(
                parking.getName(),
                parking.getCapacity(),
                occupied,
                available,
                occupancyRate
        );
    }
}