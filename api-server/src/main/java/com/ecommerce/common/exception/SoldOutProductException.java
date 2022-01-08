package com.ecommerce.common.exception;

public class SoldOutProductException extends OrderException {
    public SoldOutProductException(String message) {
        super(message);
    }
}
