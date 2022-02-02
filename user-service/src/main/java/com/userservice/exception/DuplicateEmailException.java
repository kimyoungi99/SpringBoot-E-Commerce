package com.userservice.exception;

public class DuplicateEmailException extends DataException{

    public DuplicateEmailException(String message) {
        super(message);
    }
}
