package com.userservice.exception;

public class KafkaConnectionException extends RemoteClientException{

    public KafkaConnectionException(String message) {
        super(message);
    }
}
