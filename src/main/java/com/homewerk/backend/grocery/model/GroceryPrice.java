package com.homewerk.backend.grocery.model;

import java.math.BigDecimal;
import java.time.Instant;

public record GroceryPrice(
        String productId,
        String storeId,
        BigDecimal regularPrice,
        BigDecimal promoPrice,
        Instant effectiveDate,
        Instant expirationDate,
        String size,
        String soldBy,
        String stockLevel,
        Boolean curbside,
        Boolean delivery,
        Boolean inStore,
        Boolean shipToHome
) {
}