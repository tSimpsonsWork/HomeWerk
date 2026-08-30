package com.homewerk.backend.grocery.dto.kroger;

import java.util.List;

public record KrogerLocationSearchResponse(
        List<KrogerLocationResponse> data
) {
}