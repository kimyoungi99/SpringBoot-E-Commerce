package com.categoryserver.exception;

public class KafkaConnectionException extends RemoteClientException{

    public KafkaConnectionException(String message) {
        super(message);
    }
}
