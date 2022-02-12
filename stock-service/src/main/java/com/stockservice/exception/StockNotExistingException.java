package com.stockservice.exception;

public class StockNotExistingException extends DataException{

    public StockNotExistingException(String message) {
        super(message);
    }
}
