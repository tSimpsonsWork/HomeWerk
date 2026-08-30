package com.homewerk.backend.grocery.model;

import com.homewerk.backend.grocery.model.enums.PromotionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GroceryDeal(
        String productId,
        String storeId,
        PromotionType promotionType,
        BigDecimal advertisedPrice,
        LocalDate validFrom,
        LocalDate validTo
) {}