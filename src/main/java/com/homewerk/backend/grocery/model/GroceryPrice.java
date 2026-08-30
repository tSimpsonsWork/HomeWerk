package com.homewerk.backend.grocery.model;

import java.math.BigDecimal;

public record GroceryPrice(
        String productId,
        String storeId,
        BigDecimal regularPrice,
        BigDecimal salePrice,
        String unit,
        String size
) {}