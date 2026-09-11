package com.homewerk.backend.user.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class PasswordResetTokenService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int TOKEN_BYTES = 32;
    private static final String SHA_256 = "SHA-256";

    public String generateRawToken() {

        byte[] randomBytes = new byte[TOKEN_BYTES];

        SECURE_RANDOM.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    public String hashToken(String rawToken) {

        try {
            MessageDigest digest =
                    MessageDigest.getInstance(SHA_256);

            byte[] hash =
                    digest.digest(
                            rawToken.getBytes(StandardCharsets.UTF_8)
                    );

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                    "SHA-256 algorithm is unavailable.",
                    exception
            );
        }
    }
}