package com.categoryserver.exception;

public class CategoryNotExistingException extends DataException{

    public CategoryNotExistingException(String message) {
        super(message);
    }
}
