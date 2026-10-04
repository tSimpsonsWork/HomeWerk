package com.homewerk.backend.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "password_reset_attempts")
@Getter
@Setter
public class PasswordResetAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "email_hash",
            nullable = false,
            length = 64
    )
    private String emailHash;

    @Column(
            name = "ip_address",
            nullable = false,
            length = 45
    )
    private String ipAddress;

    @Column(
            name = "created_at",
            nullable = false
    )
    private Instant createdAt;
}