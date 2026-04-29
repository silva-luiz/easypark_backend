package com.example.easypark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "exits")
@Getter
@Setter
public class Exit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "entry_id", nullable = false, unique = true)
    private Entry entry;

    @Column(nullable = false)
    private LocalDateTime exitTime;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private boolean paid;
}