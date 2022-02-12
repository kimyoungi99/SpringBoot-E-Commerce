package com.stockservice.exception;

public class KafkaConnectionException extends RemoteClientException{

    public KafkaConnectionException(String message) {
        super(message);
    }
}
