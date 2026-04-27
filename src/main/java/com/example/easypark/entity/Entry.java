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

    // RELACIONAMENTO COM VEHICLE
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // DATA DE ENTRADA
    @Column(nullable = false, updatable = false)
    private LocalDateTime entryTime;

    // TIPO DE CLIENTE
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    // STATUS DA ENTRADA
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryStatus status;

    // AUTOMATIZAÇÃO AO SALVAR
    @PrePersist
    public void prePersist() {
        this.entryTime = LocalDateTime.now();
        this.status = EntryStatus.OPEN;
    }
}