package com.stockservice.exception;

public class DataResponseException extends RemoteClientException {

    public DataResponseException(String message) {
        super(message);
    }
}