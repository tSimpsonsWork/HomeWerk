package com.homewerk.backend.grocery.dto.kroger;

public record KrogerServingSizeResponse(
        Integer quantity,
        KrogerUnitOfMeasureResponse unitOfMeasure
) {
}