package com.homewerk.backend.grocery.mapper;

import com.homewerk.backend.grocery.dto.kroger.KrogerLocationResponse;
import com.homewerk.backend.grocery.model.GroceryStore;
import com.homewerk.backend.grocery.model.enums.StoreType;
import org.springframework.stereotype.Component;

@Component
public class KrogerLocationMapper {

    private static final String STORE = StoreType.KROGER.getDisplayName();

    public GroceryStore toGroceryStore(KrogerLocationResponse location) {

        return new GroceryStore(
                location.locationId(),
                STORE,
                location.chain(),
                location.name(),
                location.address().addressLine1(),
                location.address().city(),
                location.address().state(),
                location.address().zipCode(),
                location.geolocation().latitude(),
                location.geolocation().longitude()
        );
    }
}