package com.example.easypark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // USER EMAIL (LOGIN)
    @Column(nullable = false, unique = true)
    private String email;

    // USER PASSWORD (HASHED)
    @Column(nullable = false)
    private String password;

    // RELATION WITH PARKING
    @ManyToOne(optional = true)
    @JoinColumn(name = "parking_id", nullable = true)
    private Parking parking;
}