package com.apigateway.exception;

public class OrderServiceConnectionException extends RemoteClientException {

    public OrderServiceConnectionException(String message) {
        super(message);
    }
}
