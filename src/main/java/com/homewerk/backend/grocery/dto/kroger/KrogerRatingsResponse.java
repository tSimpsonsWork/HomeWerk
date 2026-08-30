package com.homewerk.backend.grocery.dto.kroger;

public record KrogerRatingsResponse(
        Double averageOverallRating,
        Integer totalReviewCount
) {
}