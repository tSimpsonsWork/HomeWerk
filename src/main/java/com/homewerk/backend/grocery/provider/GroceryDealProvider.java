package com.homewerk.backend.grocery.provider;

import com.homewerk.backend.grocery.model.GroceryDeal;

import java.util.List;

public interface GroceryDealProvider {

    List<GroceryDeal> getDeals(String storeId);
}