package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.client.KrogerClient;
import com.homewerk.backend.grocery.dto.kroger.KrogerProductSearchResponse;
import com.homewerk.backend.grocery.mapper.KrogerLocationMapper;
import com.homewerk.backend.grocery.mapper.KrogerProductMapper;
import com.homewerk.backend.grocery.model.GroceryPrice;
import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.model.GroceryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.homewerk.backend.grocery.util.GroceryDataUtils.toBigDecimal;
import static com.homewerk.backend.grocery.util.GroceryDataUtils.toInstant;

@Component
@RequiredArgsConstructor
public class KrogerProvider
        implements GroceryProvider,
        GroceryStoreProvider,
        GroceryPriceProvider {

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
    public GroceryPrice getPrice(
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
                .findFirst()
                .map(item -> new GroceryPrice(
                        productId,
                        storeId,
                        item.price() != null
                                ? toBigDecimal(item.price().regular())
                                : null,
                        item.price() != null
                                ? toBigDecimal(item.price().promo())
                                : null,
                        item.price() != null
                                ? toInstant(item.price().effectiveDate())
                                : null,
                        item.price() != null
                                ? toInstant(item.price().expirationDate())
                                : null,
                        item.size(),
                        item.soldBy(),
                        item.inventory() != null
                                ? item.inventory().stockLevel()
                                : null,
                        item.fulfillment() != null
                                ? item.fulfillment().curbside()
                                : null,
                        item.fulfillment() != null
                                ? item.fulfillment().delivery()
                                : null,
                        item.fulfillment() != null
                                ? item.fulfillment().inStore()
                                : null,
                        item.fulfillment() != null
                                ? item.fulfillment().shipToHome()
                                : null
                ))
                .orElse(null);
    }
}