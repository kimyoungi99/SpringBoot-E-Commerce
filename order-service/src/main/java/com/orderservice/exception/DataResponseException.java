package com.orderservice.exception;

public class DataResponseException extends RemoteClientException {

    public DataResponseException(String message) {
        super(message);
    }
}