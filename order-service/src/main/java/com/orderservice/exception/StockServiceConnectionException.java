package com.orderservice.exception;

public class StockServiceConnectionException extends RemoteClientException {

    public StockServiceConnectionException(String message) {
        super(message);
    }
}
