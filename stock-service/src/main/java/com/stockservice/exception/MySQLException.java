package com.stockservice.exception;

public class MySQLException extends DatabaseConnectionException{
    public MySQLException(String message) {
        super(message);
    }
}
