package com.orderservice.exception;

public class RemoteClientException extends RuntimeException {

    public RemoteClientException(String message) {
        super(message);
    }
}