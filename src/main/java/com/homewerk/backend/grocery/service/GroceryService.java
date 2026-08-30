package com.homewerk.backend.grocery.service;

import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.provider.GroceryProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryService {

    private final GroceryProvider groceryProvider;

    public List<GroceryProduct> searchProducts(String query) {
        return groceryProvider.searchProducts(query);
    }
}