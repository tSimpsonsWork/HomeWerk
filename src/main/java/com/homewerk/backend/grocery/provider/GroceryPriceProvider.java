package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.model.GroceryPrice;

public interface GroceryPriceProvider {
    GroceryPrice getPrice(String productId, String storeId);
}