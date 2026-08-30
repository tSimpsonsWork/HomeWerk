package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.client.KrogerClient;
import com.homewerk.backend.grocery.dto.kroger.KrogerProductResponse;
import com.homewerk.backend.grocery.mapper.KrogerProductMapper;
import com.homewerk.backend.grocery.model.GroceryProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KrogerProvider implements GroceryProvider {

    private final KrogerClient krogerClient;
    private final KrogerProductMapper mapper;

    @Override
    public List<GroceryProduct> searchProducts(String query) {

        return krogerClient.searchProducts(query)
                .data()
                .stream()
                .map(mapper::toGroceryProduct)
                .toList();
    }
}