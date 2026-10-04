package com.homewerk.backend.user.service;

import com.homewerk.backend.user.model.PasswordResetAttempt;
import com.homewerk.backend.user.repository.PasswordResetAttemptRepository;
import com.homewerk.backend.utils.HashingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PasswordResetRateLimitService {

    private static final int MAX_EMAIL_ATTEMPTS_30_MINUTES = 3;
    private static final int MAX_EMAIL_ATTEMPTS_24_HOURS = 6;

    private static final int MAX_IP_ATTEMPTS_30_MINUTES = 10;
    private static final int MAX_IP_ATTEMPTS_24_HOURS = 25;

    private static final Duration SHORT_WINDOW =
            Duration.ofMinutes(30);

    private static final Duration DAILY_WINDOW =
            Duration.ofHours(24);

    private final PasswordResetAttemptRepository passwordResetAttemptRepository;

    public boolean isAllowed(String normalizedEmail, String ipAddress) {

        String emailHash =
                HashingUtil.sha256(normalizedEmail);

        Instant now = Instant.now();

        Instant shortCutoff =
                now.minus(SHORT_WINDOW);

        Instant dailyCutoff =
                now.minus(DAILY_WINDOW);

        long emailAttemptsShort =
                passwordResetAttemptRepository
                        .countByEmailHashAndCreatedAtAfter(
                                emailHash,
                                shortCutoff
                        );

        long emailAttemptsDaily =
                passwordResetAttemptRepository
                        .countByEmailHashAndCreatedAtAfter(
                                emailHash,
                                dailyCutoff
                        );

        long ipAttemptsShort =
                passwordResetAttemptRepository
                        .countByIpAddressAndCreatedAtAfter(
                                ipAddress,
                                shortCutoff
                        );

        long ipAttemptsDaily =
                passwordResetAttemptRepository
                        .countByIpAddressAndCreatedAtAfter(
                                ipAddress,
                                dailyCutoff
                        );

        return emailAttemptsShort < MAX_EMAIL_ATTEMPTS_30_MINUTES
                && emailAttemptsDaily < MAX_EMAIL_ATTEMPTS_24_HOURS
                && ipAttemptsShort < MAX_IP_ATTEMPTS_30_MINUTES
                && ipAttemptsDaily < MAX_IP_ATTEMPTS_24_HOURS;
    }

    public void recordAttempt(
            String normalizedEmail,
            String ipAddress
    ) {
        String emailHash =
                HashingUtil.sha256(normalizedEmail);

        PasswordResetAttempt attempt =
                new PasswordResetAttempt();

        attempt.setEmailHash(emailHash);
        attempt.setIpAddress(ipAddress);
        attempt.setCreatedAt(Instant.now());

        passwordResetAttemptRepository.save(attempt);
    }
}