package com.homewerk.backend.grocery.provider.model;

import com.homewerk.backend.grocery.model.GroceryAvailability;
import com.homewerk.backend.grocery.model.GroceryPrice;

public record KrogerProductLocationData(
        GroceryPrice price,
        GroceryAvailability availability
) {
}