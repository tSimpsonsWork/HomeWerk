package com.homewerk.backend.grocery.dto.kroger;

public record KrogerTemperatureResponse(
        String indicator,
        boolean heatSensitive
) {
}