package com.homewerk.backend.grocery.dto.kroger;

public record KrogerNutrientResponse(
        String code,
        String description,
        String displayName,
        Double percentDailyIntake,
        Double quantity,
        KrogerUnitOfMeasureResponse unitOfMeasure
) {
}