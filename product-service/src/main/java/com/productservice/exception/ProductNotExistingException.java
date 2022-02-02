package com.productservice.exception;

public class ProductNotExistingException extends DataException{

    public ProductNotExistingException(String message) {
        super(message);
    }
}
