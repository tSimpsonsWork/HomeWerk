package com.homewerk.backend.grocery.model;

import java.util.List;

public record GroceryProduct(
        String productId,
        String upc,
        String brand,
        String name,
        List<String> categories,
        String size,
        String imageUrl,
        GroceryNutrition nutrition
) {
}