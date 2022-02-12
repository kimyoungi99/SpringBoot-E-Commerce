package com.stockservice.exception;

public class WrongEventTypeException extends DataException{

    public WrongEventTypeException(String message) {
        super(message);
    }
}
