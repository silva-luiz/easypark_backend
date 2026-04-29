package com.example.easypark.service;

import com.example.easypark.dto.ExitResponseDTO;
import com.example.easypark.entity.Entry;
import com.example.easypark.entity.EntryStatus;
import com.example.easypark.entity.Exit;
import com.example.easypark.entity.CustomerType;
import com.example.easypark.exception.BusinessException;
import com.example.easypark.repository.EntryRepository;
import com.example.easypark.repository.ExitRepository;
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

    public ExitResponseDTO registerExit(String plate) {

        Entry entry = entryRepository
                .findByVehiclePlateAndStatus(plate, EntryStatus.OPEN)
                .orElseThrow(() -> new BusinessException("Vehicle not found or already exited"));

        BigDecimal amount = calculateAmount(entry);

        Exit exit = new Exit();
        exit.setEntry(entry);
        exit.setExitTime(LocalDateTime.now());
        exit.setPaid(true);
        exit.setTotalAmount(amount);

        entry.setStatus(EntryStatus.CLOSED);
        entryRepository.save(entry);

        Exit saved = exitRepository.save(exit);

        return new ExitResponseDTO(
                saved.getId(),
                saved.getEntry().getVehicle().getPlate(),
                saved.getExitTime(),
                saved.getTotalAmount(),
                saved.isPaid()
        );
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