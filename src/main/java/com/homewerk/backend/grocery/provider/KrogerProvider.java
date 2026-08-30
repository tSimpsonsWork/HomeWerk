package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.client.KrogerClient;
import com.homewerk.backend.grocery.dto.kroger.KrogerDateResponse;
import com.homewerk.backend.grocery.dto.kroger.KrogerProductSearchResponse;
import com.homewerk.backend.grocery.mapper.KrogerLocationMapper;
import com.homewerk.backend.grocery.mapper.KrogerProductMapper;
import com.homewerk.backend.grocery.model.GroceryPrice;
import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.model.GroceryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KrogerProvider implements GroceryProvider, GroceryStoreProvider, GroceryPriceProvider {

    private final KrogerClient krogerClient;
    private final KrogerProductMapper mapper;
    private final KrogerLocationMapper locationMapper;

    @Override
    public List<GroceryProduct> searchProducts(String query) {
        return krogerClient.searchProducts(query)
                .data()
                .stream()
                .map(mapper::toGroceryProduct)
                .toList();
    }

    @Override
    public List<GroceryStore> findStores(String postalCode) {
        return krogerClient.findLocations(postalCode)
                .data()
                .stream()
                .map(locationMapper::toGroceryStore)
                .toList();
    }

    @Override
    public List<GroceryPrice> getPrices(
            String productId,
            String storeId) {

        KrogerProductSearchResponse response =
                krogerClient.searchProductsAtLocation(
                        productId,
                        storeId
                );

        return response.data()
                .stream()
                .flatMap(product -> product.items().stream())
                .filter(item -> item.price() != null)
                .map(item -> new GroceryPrice(
                        productId,
                        storeId,
                        toBigDecimal(item.price().regular()),
                        toBigDecimal(item.price().promo()),
                        toInstant(item.price().effectiveDate()),
                        toInstant(item.price().expirationDate()),
                        item.size(),
                        item.soldBy()
                ))
                .toList();
    }


    private BigDecimal toBigDecimal(Double value) {
        return value == null
                ? null
                : BigDecimal.valueOf(value);
    }

    private Instant toInstant(KrogerDateResponse date) {
        return date == null || date.value() == null
                ? null
                : Instant.parse(date.value());
    }
}