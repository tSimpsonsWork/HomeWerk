package com.homewerk.backend.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class HashingUtil {

    private static final String SHA_256 = "SHA-256";

    private HashingUtil() {
    }

    public static String sha256(String value) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance(SHA_256);

            byte[] hash =
                    digest.digest(
                            value.getBytes(StandardCharsets.UTF_8)
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