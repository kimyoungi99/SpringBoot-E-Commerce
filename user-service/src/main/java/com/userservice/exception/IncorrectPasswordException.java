package com.userservice.exception;

public class IncorrectPasswordException extends AuthenticationException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
