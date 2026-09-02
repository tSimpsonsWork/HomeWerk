package com.homewerk.backend.utils;

import java.text.Normalizer;

public final class AiPromptValidationUtil {

    private AiPromptValidationUtil() {}

    public static final int MAX_PROMPT_LENGTH = 2000;
    public static final int MIN_MEANINGFUL_LENGTH = 2;

    public static String normalizePrompt(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Message is required.");
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFKC)
                .replace('\u0000', ' ')
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "")
                .trim()
                .replaceAll("\\s+", " ");

        if (normalized.isBlank()) {
            throw new IllegalArgumentException("Message cannot be empty.");
        }

        if (normalized.length() > MAX_PROMPT_LENGTH) {
            throw new IllegalArgumentException("Message is too long.");
        }

        if (normalized.length() < MIN_MEANINGFUL_LENGTH) {
            throw new IllegalArgumentException("Message is too short.");
        }

        if (isLowSignalJunk(normalized)) {
            throw new IllegalArgumentException("Please enter a clearer question or request.");
        }

        return normalized;
    }

    static boolean isLowSignalJunk(String value) {
        String stripped = value.replaceAll("\\s+", "");

        if (stripped.length() < 2) {
            return true;
        }

        if (stripped.matches("^(.)\\1{5,}$")) {
            return true;
        }

        if (stripped.matches("^[^\\p{L}\\p{N}]{6,}$")) {
            return true;
        }

        return false;
    }
}
