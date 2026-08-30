package com.homewerk.backend.grocery.dto.kroger;

import java.util.List;

public record KrogerImageResponse(
        String perspective,
        Boolean featured,
        List<KrogerImageSizeResponse> sizes
) {
}