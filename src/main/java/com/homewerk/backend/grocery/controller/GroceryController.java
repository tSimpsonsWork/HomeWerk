package com.homewerk.backend.grocery.controller;

import com.homewerk.backend.grocery.model.GroceryAvailability;
import com.homewerk.backend.grocery.model.GroceryPrice;
import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.model.GroceryStore;
import com.homewerk.backend.grocery.service.GroceryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grocery")
@RequiredArgsConstructor
public class GroceryController {
    private final GroceryService groceryService;

    //GET http://localhost:8080/api/grocery/search?query=milk
    @GetMapping("/search")
    public List<GroceryProduct> searchProducts(@RequestParam String query) {
        return groceryService.searchProducts(query);
    }

    //GET http://localhost:8080/api/grocery/stores?zipCode=45202
    @GetMapping("/stores")
    public List<GroceryStore> findStores(@RequestParam String zipCode) {
        return groceryService.findStores(zipCode);
    }

    //GET http://localhost:8080/api/grocery/prices?productId=0001111041700&storeId=01400513
    @GetMapping("/prices")
    public List<GroceryPrice> getPrices(@RequestParam String productId, @RequestParam String storeId) {
        return groceryService.getPrices(productId, storeId);
    }

    //GET http://localhost:8080/api/grocery/availability?productId=0001111041700&storeId=01400513
    @GetMapping("/availability")
    public GroceryAvailability getAvailability(@RequestParam String productId, @RequestParam String storeId) {
        return groceryService.getAvailability(productId, storeId);
    }


}