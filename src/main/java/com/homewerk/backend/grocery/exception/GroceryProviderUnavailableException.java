package com.homewerk.backend.grocery.exception;

public class GroceryProviderUnavailableException extends RuntimeException {

    public GroceryProviderUnavailableException(String message) {
        super(message);
    }

    public GroceryProviderUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}