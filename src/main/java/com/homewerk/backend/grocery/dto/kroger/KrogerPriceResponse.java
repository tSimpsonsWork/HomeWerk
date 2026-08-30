package com.homewerk.backend.grocery.dto.kroger;

public record KrogerPriceResponse(
        Double regular,
        Double promo,
        KrogerDateResponse effectiveDate,
        KrogerDateResponse expirationDate
) {
}