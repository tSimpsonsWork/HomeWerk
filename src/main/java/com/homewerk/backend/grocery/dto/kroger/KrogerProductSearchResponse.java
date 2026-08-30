package com.homewerk.backend.grocery.dto.kroger;

import java.util.List;

public record KrogerProductSearchResponse(
        List<KrogerProductResponse> data
) {
}