package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.model.GroceryAvailability;

public interface GroceryAvailabilityProvider {

    GroceryAvailability getAvailability(
            String productId,
            String storeId
    );
}