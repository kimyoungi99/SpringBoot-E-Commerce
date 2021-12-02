package com.ecommerce.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;

public interface ResponseBuilder {

    ResponseEntity<HttpResponseDto> jsonResponseBuild(HttpStatus httpStatus, String message, Object data);
}
