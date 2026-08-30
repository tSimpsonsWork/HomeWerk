package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.model.GroceryProduct;

import java.util.List;

public interface GroceryProvider {
    List<GroceryProduct> searchProducts(String query);
}