package com.homewerk.backend.user.exception;

import com.homewerk.backend.common.dto.ApiErrorResponse;
import com.homewerk.backend.common.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleEmailAlreadyExists(
            EmailAlreadyExistsException exception) {

        return new ApiErrorResponse(
                ErrorCode.ACCOUNT_CREATION_FAILED,
                "Unable to create account."
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleBadCredentials(
            InvalidCredentialsException exception) {

        return new ApiErrorResponse(
                ErrorCode.INVALID_CREDENTIALS,
                "Invalid email or password."
        );
    }

    @ExceptionHandler(InvalidPasswordRecoveryTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidPasswordRecoveryToken(
            InvalidPasswordRecoveryTokenException exception) {

        return new ApiErrorResponse(
                ErrorCode.INVALID_PASSWORD_RECOVERY_TOKEN,
                "Invalid or expired recovery token."
        );
    }
}