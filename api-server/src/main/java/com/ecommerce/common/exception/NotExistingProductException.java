package com.ecommerce.common.exception;

public class NotExistingProductException extends OrderException{

    public NotExistingProductException(String message) {
        super(message);
    }
}
