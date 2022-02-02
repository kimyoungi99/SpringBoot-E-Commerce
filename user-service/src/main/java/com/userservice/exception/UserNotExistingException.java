package com.userservice.exception;

public class UserNotExistingException extends DataException{

    public UserNotExistingException(String message) {
        super(message);
    }
}
