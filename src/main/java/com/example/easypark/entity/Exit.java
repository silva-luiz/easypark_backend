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

    // RELACIONAMENTO COM ENTRY
    @OneToOne(optional = false)
    @JoinColumn(name = "entry_id", nullable = false, unique = true)
    private Entry entry;

    // DATA DE SAÍDA
    @Column(nullable = false)
    private LocalDateTime exitTime;

    // VALOR COBRADO
    @Column(nullable = false)
    private BigDecimal totalAmount;

    // STATUS DE PAGAMENTO
    @Column(nullable = false)
    private Boolean paid;

    // AUTOMATIZAÇÃO
    @PrePersist
    public void prePersist() {
        this.exitTime = LocalDateTime.now();
        this.paid = false;
    }
}