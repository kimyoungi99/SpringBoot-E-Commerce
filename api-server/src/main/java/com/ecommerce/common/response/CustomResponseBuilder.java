package com.ecommerce.common.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class CustomResponseBuilder implements ResponseBuilder{
    @Override
    public ResponseEntity<HttpResponseDto> jsonResponseBuild(HttpStatus httpStatus, String message, Object data) {
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        body.setHttpStatus(httpStatus);
        body.setData(data);
        body.setMessage(message);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
