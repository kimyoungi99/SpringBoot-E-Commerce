package com.ecommerce.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseBuilder {

    ResponseEntity<HttpResponseDto> jsonResponseBuild(HttpStatus httpStatus, String message, Object data);
}
