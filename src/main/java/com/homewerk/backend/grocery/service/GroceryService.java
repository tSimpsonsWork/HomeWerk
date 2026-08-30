package com.homewerk.backend.grocery.service;

import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.model.GroceryStore;
import com.homewerk.backend.grocery.provider.GroceryProvider;
import com.homewerk.backend.grocery.provider.GroceryStoreProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryService {

    private final GroceryProvider groceryProvider;
    private final GroceryStoreProvider groceryStoreProvider;

    public List<GroceryProduct> searchProducts(String query) {
        return groceryProvider.searchProducts(query);
    }
    public List<GroceryStore> findStores(String postalCode) {return groceryStoreProvider.findStores(postalCode);
    }
}