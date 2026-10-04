package com.homewerk.backend.user.service;

import com.homewerk.backend.utils.HashingUtil;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordResetTokenService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int TOKEN_BYTES = 32;

    public String generateRawToken() {

        byte[] randomBytes = new byte[TOKEN_BYTES];

        SECURE_RANDOM.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    public String hashToken(String rawToken) {
        return HashingUtil.sha256(rawToken);
    }
}