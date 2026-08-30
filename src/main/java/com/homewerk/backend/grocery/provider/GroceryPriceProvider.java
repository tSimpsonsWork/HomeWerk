package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.model.GroceryPrice;

import java.util.List;

public interface GroceryPriceProvider {

    List<GroceryPrice> getPrices(
            String productId,
            String storeId
    );
}