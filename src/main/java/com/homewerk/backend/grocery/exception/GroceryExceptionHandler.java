package com.homewerk.backend.grocery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GroceryExceptionHandler {

    @ExceptionHandler(GroceryProviderUnavailableException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map<String, String> handleProviderUnavailable(GroceryProviderUnavailableException ex) {
        return Map.of(
                "error", "GROCERY_PROVIDER_UNAVAILABLE",
                "message", "The grocery provider is currently unavailable."
        );
    }
}