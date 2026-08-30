package com.homewerk.backend.grocery.mapper;

import com.homewerk.backend.grocery.dto.kroger.KrogerNutritionResponse;
import com.homewerk.backend.grocery.dto.kroger.KrogerNutrientResponse;
import com.homewerk.backend.grocery.dto.kroger.KrogerProductResponse;
import com.homewerk.backend.grocery.model.GroceryNutrition;
import com.homewerk.backend.grocery.model.GroceryProduct;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KrogerProductMapper {

    public GroceryProduct toGroceryProduct(KrogerProductResponse product) {

        return new GroceryProduct(
                product.productId(),
                product.upc(),
                product.brand(),
                product.description(),
                product.categories(),
                extractSize(product),
                extractImageUrl(product),
                extractNutrition(product)
        );
    }

    private String extractSize(KrogerProductResponse product) {

        if (product.items() == null || product.items().isEmpty()) {
            return null;
        }

        return product.items().get(0).size();
    }

    private String extractImageUrl(KrogerProductResponse product) {
        // We'll implement this next.
        return null;
    }

    private GroceryNutrition extractNutrition(
            KrogerProductResponse product) {

        if (product.nutritionInformation() == null ||
                product.nutritionInformation().isEmpty()) {
            return null;
        }

        KrogerNutritionResponse nutrition =
                product.nutritionInformation().get(0);

        return new GroceryNutrition(
                nutrition.ingredientStatement(),
                product.allergensDescription(),
                extractCalories(nutrition),
                extractNutrient(nutrition, "PRO-"),
                extractNutrient(nutrition, "FAT"),
                extractNutrient(nutrition, "CHO-"),
                extractNutrient(nutrition, "SUGAR"),
                extractNutrient(nutrition, "NA")
        );
    }

    private Integer extractCalories(KrogerNutritionResponse nutrition) {
        Double value = extractNutrient(nutrition, "ENER-");
        return value == null ? null : value.intValue();
    }

    private Double extractNutrient(
            KrogerNutritionResponse nutrition,
            String code) {

        if (nutrition.nutrients() == null) {
            return null;
        }

        return nutrition.nutrients()
                .stream()
                .filter(nutrient -> code.equals(nutrient.code()))
                .map(KrogerNutrientResponse::quantity)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}