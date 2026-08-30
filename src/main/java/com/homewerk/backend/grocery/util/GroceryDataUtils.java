package com.homewerk.backend.grocery.util;

import com.homewerk.backend.grocery.dto.kroger.KrogerDateResponse;

import java.math.BigDecimal;
import java.time.Instant;

public final class GroceryDataUtils {

    private GroceryDataUtils() {
    }

    public static BigDecimal toBigDecimal(Double value) {
        return value == null
                ? null
                : BigDecimal.valueOf(value);
    }

    public static Instant toInstant(KrogerDateResponse date) {
        return date == null || date.value() == null
                ? null
                : Instant.parse(date.value());
    }
}