package com.homewerk.backend.user.repository;

import com.homewerk.backend.user.model.PasswordResetToken;
import com.homewerk.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    void deleteByUser(User user);
}