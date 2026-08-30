package com.homewerk.backend.grocery.controller;

import com.homewerk.backend.grocery.model.GroceryProduct;
import com.homewerk.backend.grocery.model.GroceryStore;
import com.homewerk.backend.grocery.service.GroceryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    //GET http://localhost:8080/api/grocery/stores?zipCode=33009
    @GetMapping("/stores")
    public List<GroceryStore> findStores(@RequestParam String zipCode) {
        return groceryService.findStores(zipCode);
    }

}