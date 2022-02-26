package com.apigateway.exception;

import com.apigateway.dto.ExceptionResponseDto;

import java.util.Collection;
import java.util.Map;

public class FeignClientException extends RemoteClientException {

    public FeignClientException(String message) {
        super(message);
    }
}
