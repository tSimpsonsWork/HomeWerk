package com.homewerk.backend.grocery.client;

import com.homewerk.backend.config.KrogerProperties;
import com.homewerk.backend.grocery.dto.kroger.KrogerLocationSearchResponse;
import com.homewerk.backend.grocery.dto.kroger.KrogerProductDetailsResponse;
import com.homewerk.backend.grocery.dto.kroger.KrogerTokenResponse;
import com.homewerk.backend.grocery.dto.kroger.KrogerProductSearchResponse;
import com.homewerk.backend.grocery.exception.GroceryProviderUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class KrogerClient {

    private static final String TOKEN_URL =
            "https://api.kroger.com/v1/connect/oauth2/token";

    private static final String API_BASE_URL =
            "https://api.kroger.com";

    private final KrogerProperties krogerProperties;

    private final RestClient restClient = RestClient.builder()
            .baseUrl(API_BASE_URL)
            .build();

    public String getAccessToken() {

        if (!krogerProperties.isConfigured()) {
            throw new GroceryProviderUnavailableException("Kroger integration is not configured");
        }

        String credentials =
                krogerProperties.getClientId()
                        + ":"
                        + krogerProperties.getClientSecret();

        String encodedCredentials =
                Base64.getEncoder()
                        .encodeToString(
                                credentials.getBytes(StandardCharsets.UTF_8)
                        );

        MultiValueMap<String, String> formData =
                new LinkedMultiValueMap<>();

        formData.add("grant_type", "client_credentials");
        formData.add("scope", "product.compact");

        try {
            KrogerTokenResponse response = restClient.post()
                    .uri(TOKEN_URL)
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Basic " + encodedCredentials
                    )
                    .contentType(
                            MediaType.APPLICATION_FORM_URLENCODED
                    )
                    .body(formData)
                    .retrieve()
                    .body(KrogerTokenResponse.class);

            if (response == null || response.accessToken() == null) {
                throw new GroceryProviderUnavailableException("Kroger did not return an access token");
            }

            return response.accessToken();

        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new GroceryProviderUnavailableException("Kroger authentication failed");
        }
    }

    public KrogerProductSearchResponse searchProducts(String query) {

        String token = getAccessToken();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/products")
                        .queryParam("filter.term", query)
                        .queryParam("filter.limit", 10)
                        .build())
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token
                )
                .header(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .retrieve()
                .body(KrogerProductSearchResponse.class);
    }

    public KrogerLocationSearchResponse findLocations(String postalCode) {

        String token = getAccessToken();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/locations")
                        .queryParam("filter.zipCode.near", postalCode)
                        .queryParam("filter.radiusInMiles", 50)
                        .queryParam("filter.limit", 10)
                        .queryParam("filter.chain", "KROGER")
                        .build())
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token
                )
                .header(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .retrieve()
                .body(KrogerLocationSearchResponse.class);
    }

    public KrogerProductDetailsResponse getProduct(
            String productId,
            String locationId) {

        String token = getAccessToken();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/products/{productId}")
                        .queryParam("filter.locationId", locationId)
                        .build(productId))
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token
                )
                .header(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .retrieve()
                .body(KrogerProductDetailsResponse.class);
    }

    public KrogerProductSearchResponse searchProductsAtLocation(
            String query,
            String locationId) {

        String token = getAccessToken();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/products")
                        .queryParam("filter.term", query)
                        .queryParam("filter.locationId", locationId)
                        .queryParam("filter.limit", 10)
                        .build())
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token
                )
                .header(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .retrieve()
                .body(KrogerProductSearchResponse.class);
    }
}