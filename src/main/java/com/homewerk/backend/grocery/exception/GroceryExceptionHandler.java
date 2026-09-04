package com.homewerk.backend.grocery.exception;

import com.homewerk.backend.common.dto.ApiErrorResponse;
import com.homewerk.backend.common.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GroceryExceptionHandler {

    @ExceptionHandler(GroceryProviderUnavailableException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiErrorResponse handleProviderUnavailable(
            GroceryProviderUnavailableException ex) {return new ApiErrorResponse(
                ErrorCode.GROCERY_PROVIDER_UNAVAILABLE,
                "The grocery provider is currently unavailable."
        );
    }
}