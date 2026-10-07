package com.homewerk.backend.user.exception;

public class InvalidPasswordRecoveryTokenException
        extends RuntimeException {

    public InvalidPasswordRecoveryTokenException() {
        super("Invalid or expired recovery token.");
    }
}