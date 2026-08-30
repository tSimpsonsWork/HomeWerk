package com.homewerk.backend.grocery.model;

public record GroceryNutrition(
        String ingredients,
        String allergens,
        Integer calories,
        Double proteinGrams,
        Double totalFatGrams,
        Double carbohydratesGrams,
        Double sugarGrams,
        Double sodiumMilligrams
) {}