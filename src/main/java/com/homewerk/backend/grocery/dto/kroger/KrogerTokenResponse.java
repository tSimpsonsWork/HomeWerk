package com.homewerk.backend.grocery.dto.kroger;

import com.fasterxml.jackson.annotation.JsonProperty;
public record KrogerTokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("expires_in")
        long expiresIn,
        String scope
) {

}