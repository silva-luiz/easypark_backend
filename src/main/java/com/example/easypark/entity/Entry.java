package com.example.easypark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "entries")
@Getter
@Setter
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Association with Vehicle
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // Entry date
    @Column(nullable = false, updatable = false)
    private LocalDateTime entryTime;

    // Customer Type
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    // Entry Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryStatus status;

    // Update on save
    @PrePersist
    public void prePersist() {
        this.entryTime = LocalDateTime.now();
        this.status = EntryStatus.OPEN;
    }

    // Entry -> Parking
    @ManyToOne(optional = false)
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;
}