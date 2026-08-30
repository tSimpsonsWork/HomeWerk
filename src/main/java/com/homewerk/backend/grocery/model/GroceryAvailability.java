package com.homewerk.backend.grocery.model;

public record GroceryAvailability(
        String productId,
        String storeId,
        String stockLevel,
        Boolean curbside,
        Boolean delivery,
        Boolean inStore,
        Boolean shipToHome
) {
}