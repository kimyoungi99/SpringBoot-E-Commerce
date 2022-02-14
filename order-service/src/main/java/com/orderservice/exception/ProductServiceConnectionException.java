package com.orderservice.exception;

public class ProductServiceConnectionException extends RemoteClientException {

    public ProductServiceConnectionException(String message) {
        super(message);
    }
}
