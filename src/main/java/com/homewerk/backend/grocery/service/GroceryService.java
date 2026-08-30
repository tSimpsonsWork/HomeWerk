package com.homewerk.backend.grocery.service;

import com.homewerk.backend.grocery.model.GroceryPrice;
import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.model.GroceryStore;
import com.homewerk.backend.grocery.provider.GroceryPriceProvider;
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
    private final GroceryPriceProvider groceryPriceProvider;

    public List<GroceryProduct> searchProducts(String query) {
        return groceryProvider.searchProducts(query);
    }
    public List<GroceryStore> findStores(String postalCode) {return groceryStoreProvider.findStores(postalCode);}
    public GroceryPrice getPrice(String productId, String storeId) {return groceryPriceProvider.getPrice(productId, storeId);}

}