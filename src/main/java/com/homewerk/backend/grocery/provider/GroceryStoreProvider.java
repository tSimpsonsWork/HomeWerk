package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.model.GroceryStore;

import java.util.List;

public interface GroceryStoreProvider {
    List<GroceryStore> findStores(String postalCode);
}