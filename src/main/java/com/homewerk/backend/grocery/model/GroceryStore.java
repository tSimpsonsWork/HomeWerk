package com.homewerk.backend.grocery.model;

public record GroceryStore(
        String storeId,
        String name,
        String address,
        String city,
        String state,
        String postalCode
) {}