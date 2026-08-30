package com.homewerk.backend.grocery.mapper;

import com.homewerk.backend.grocery.dto.kroger.KrogerItemResponse;
import com.homewerk.backend.grocery.model.GroceryAvailability;
import com.homewerk.backend.grocery.model.GroceryPrice;
import com.homewerk.backend.grocery.provider.model.KrogerProductLocationData;
import org.springframework.stereotype.Component;

import static com.homewerk.backend.grocery.util.GroceryDataUtils.toBigDecimal;
import static com.homewerk.backend.grocery.util.GroceryDataUtils.toInstant;

@Component
public class KrogerProductLocationMapper {

    public KrogerProductLocationData toProductLocationData(
            String productId,
            String storeId,
            KrogerItemResponse item) {

        return new KrogerProductLocationData(
                toGroceryPrice(productId, storeId, item),
                toGroceryAvailability(productId, storeId, item)
        );
    }

    private GroceryPrice toGroceryPrice(
            String productId,
            String storeId,
            KrogerItemResponse item) {

        return new GroceryPrice(
                productId,
                storeId,
                toBigDecimal(item.price().regular()),
                toBigDecimal(item.price().promo()),
                toInstant(item.price().effectiveDate()),
                toInstant(item.price().expirationDate()),
                item.size(),
                item.soldBy()
        );
    }

    private GroceryAvailability toGroceryAvailability(
            String productId,
            String storeId,
            KrogerItemResponse item) {

        return new GroceryAvailability(
                productId,
                storeId,
                item.inventory() != null
                        ? item.inventory().stockLevel()
                        : null,
                item.fulfillment() != null
                        ? item.fulfillment().curbside()
                        : null,
                item.fulfillment() != null
                        ? item.fulfillment().delivery()
                        : null,
                item.fulfillment() != null
                        ? item.fulfillment().inStore()
                        : null,
                item.fulfillment() != null
                        ? item.fulfillment().shipToHome()
                        : null
        );
    }
}