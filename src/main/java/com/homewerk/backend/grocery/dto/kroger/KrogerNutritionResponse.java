package com.homewerk.backend.grocery.dto.kroger;

import java.util.List;

public record KrogerNutritionResponse(
        String ingredientStatement,
        String dailyValueIntakeReference,
        KrogerServingSizeResponse servingSize,
        List<KrogerNutrientResponse> nutrients,
        KrogerPreparationStateResponse preparationState,
        KrogerServingsPerPackageResponse servingsPerPackage,
        String nutritionalRating
) {
}