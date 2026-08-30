package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.client.KrogerClient;
import com.homewerk.backend.grocery.dto.kroger.KrogerProductSearchResponse;
import com.homewerk.backend.grocery.mapper.KrogerLocationMapper;
import com.homewerk.backend.grocery.mapper.KrogerProductLocationMapper;
import com.homewerk.backend.grocery.mapper.KrogerProductMapper;
import com.homewerk.backend.grocery.model.GroceryAvailability;
import com.homewerk.backend.grocery.model.GroceryPrice;
import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.model.GroceryStore;
import com.homewerk.backend.grocery.provider.model.KrogerProductLocationData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KrogerProvider implements GroceryProvider, GroceryStoreProvider, GroceryPriceProvider, GroceryAvailabilityProvider {

    private final KrogerClient krogerClient;
    private final KrogerProductMapper mapper;
    private final KrogerLocationMapper locationMapper;
    private final KrogerProductLocationMapper productLocationMapper;

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

        KrogerProductLocationData data =
                getProductLocationData(productId, storeId);

        return data == null
                ? List.of()
                : List.of(data.price());
    }

    @Override
    public GroceryAvailability getAvailability(
            String productId,
            String storeId) {

        KrogerProductLocationData data =
                getProductLocationData(productId, storeId);

        return data == null
                ? null
                : data.availability();
    }



    private KrogerProductLocationData getProductLocationData(
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
                .map(item -> productLocationMapper.toProductLocationData(
                        productId,
                        storeId,
                        item
                ))
                .orElse(null);
    }
}