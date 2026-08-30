package com.homewerk.backend.grocery.dto.kroger;

import java.util.List;

public record KrogerProductResponse(
        String productId,
        String upc,
        String brand,
        String description,
        List<String> categories,
        String countryOrigin,
        boolean snapEligible,
        List<String> manufacturerDeclarations,
        String allergensDescription,
        String nonGmoClaimName,
        String organicClaimName,
        String receiptDescription,
        String warnings,
        List<KrogerItemResponse> items,
        List<KrogerImageResponse> images,
        KrogerTemperatureResponse temperature,
        KrogerRatingsResponse ratingsAndReviews,
        List<KrogerNutritionResponse> nutritionInformation
) {
}