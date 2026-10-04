package com.homewerk.backend.user.repository;

import com.homewerk.backend.user.model.PasswordResetAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface PasswordResetAttemptRepository
        extends JpaRepository<PasswordResetAttempt, Long> {

    long countByEmailHashAndCreatedAtAfter(
            String emailHash,
            Instant createdAt
    );

    long countByIpAddressAndCreatedAtAfter(
            String ipAddress,
            Instant createdAt
    );
}