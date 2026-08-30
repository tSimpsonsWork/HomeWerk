package com.homewerk.backend.grocery.dto.kroger;

public record KrogerItemResponse(
        String itemId,
        KrogerInventoryResponse inventory,
        KrogerFulfillmentResponse fulfillment,
        KrogerPriceResponse price,
        String size,
        String soldBy
) {
}