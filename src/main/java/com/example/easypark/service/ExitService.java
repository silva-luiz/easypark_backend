package com.example.easypark.service;

import com.example.easypark.dto.ExitResponseDTO;
import com.example.easypark.entity.*;
import com.example.easypark.exception.BusinessException;
import com.example.easypark.repository.EntryRepository;
import com.example.easypark.repository.ExitRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ExitService {

    private final EntryRepository entryRepository;
    private final ExitRepository exitRepository;

    private static final BigDecimal PRICE_PER_HOUR = BigDecimal.valueOf(10);

    public ExitService(EntryRepository entryRepository, ExitRepository exitRepository) {
        this.entryRepository = entryRepository;
        this.exitRepository = exitRepository;
    }

    public Exit registerExit(String plate) {

        // 🔐 1. GET LOGGED USER
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long parkingId = user.getParking().getId();

        // 2. FIND OPEN ENTRY FOR THIS VEHICLE + PARKING
        Entry entry = entryRepository
                .findByVehiclePlateAndStatus(plate, EntryStatus.OPEN)
                .orElseThrow(() -> new BusinessException("Vehicle not found or already exited"));

        // 🔥 VALIDAÇÃO CRÍTICA
        if (!entry.getParking().getId().equals(parkingId)) {
            throw new BusinessException("This vehicle does not belong to your parking");
        }

        // 3. CALCULATE AMOUNT
        BigDecimal amount = calculateAmount(entry);

        // 4. CREATE EXIT
        Exit exit = new Exit();
        exit.setEntry(entry);
        exit.setExitTime(LocalDateTime.now());
        exit.setTotalAmount(amount);
        exit.setPaid(true);

        // 5. CLOSE ENTRY
        entry.setStatus(EntryStatus.CLOSED);
        entryRepository.save(entry);

        return exitRepository.save(exit);
    }

    private BigDecimal calculateAmount(Entry entry) {

        if (entry.getCustomerType() == CustomerType.MENSALISTA) {
            return BigDecimal.ZERO;
        }

        long minutes = Duration.between(
                entry.getEntryTime(),
                LocalDateTime.now()
        ).toMinutes();

        long hours = (long) Math.ceil(minutes / 60.0);

        if (hours == 0) {
            hours = 1;
        }

        return PRICE_PER_HOUR.multiply(BigDecimal.valueOf(hours));
    }
}