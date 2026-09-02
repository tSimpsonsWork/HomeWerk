package com.homewerk.backend.utils;

public final class EmailValidationUtil {

    private EmailValidationUtil() {}

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$";

    public static String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }

    public static boolean isValidFormat(String email) {
        return normalize(email).matches(EMAIL_REGEX);
    }

    public static void validate(String email) {
        String normalized = normalize(email);

        if (normalized.isBlank() || !isValidFormat(normalized)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
    }
}
