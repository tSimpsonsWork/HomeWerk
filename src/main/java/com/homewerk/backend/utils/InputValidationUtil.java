package com.homewerk.backend.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class InputValidationUtil {

    private InputValidationUtil() {}

    public static final int MAX_NAME_LENGTH = 50;

    public static final Pattern NAME_PATTERN = Pattern.compile(
            "^[\\p{L}](?:[\\p{L} '\\-]{0,48}[\\p{L}])?$"
    );

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String normalizeBasic(String value) {
        if (value == null) {
            return "";
        }

        return Normalizer.normalize(value, Normalizer.Form.NFKC)
                .replace('\u0000', ' ')
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "")
                .trim()
                .replaceAll("\\s+", " ");
    }

    public static String normalizeName(String value, String fieldName) {
        String normalized = normalizeBasic(value);

        if (normalized.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }

        if (normalized.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(fieldName + " must be 50 characters or fewer");
        }

        if (!NAME_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(
                    fieldName + " may only contain letters, spaces, apostrophes, and hyphens"
            );
        }

        return normalized;
    }
}
