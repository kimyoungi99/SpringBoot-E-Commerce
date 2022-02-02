package com.userservice.exception;

public class DatabaseConnectionException extends RemoteClientException{

    public DatabaseConnectionException(String message) {
        super(message);
    }
}
