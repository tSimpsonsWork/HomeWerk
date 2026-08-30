package com.homewerk.backend.grocery.dto.kroger;

public record KrogerLocationResponse(
        String locationId,
        String name,
        String chain,
        KrogerAddressResponse address,
        KrogerGeolocationResponse geolocation
) {
}