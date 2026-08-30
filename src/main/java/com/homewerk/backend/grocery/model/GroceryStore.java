package com.homewerk.backend.grocery.model;

public record GroceryStore(
        String storeId,
        String retailer,
        String banner,
        String name,
        String address,
        String city,
        String state,
        String postalCode
) {}