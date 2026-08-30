package com.homewerk.backend.grocery.dto.kroger;

import java.util.List;

public record KrogerAddressResponse(
        String addressLine1,
        List<String> addressLine2,
        String city,
        String state,
        String zipCode
) {
}