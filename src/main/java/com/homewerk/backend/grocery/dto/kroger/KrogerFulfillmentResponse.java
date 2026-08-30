package com.homewerk.backend.grocery.dto.kroger;

public record KrogerFulfillmentResponse(
        Boolean curbside,
        Boolean delivery,
        Boolean inStore,
        Boolean shipToHome
) {
}