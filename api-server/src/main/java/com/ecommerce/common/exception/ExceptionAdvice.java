package com.ecommerce.common.exception;

import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.common.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionAdvice {

    private final ResponseBuilder responseBuilder;

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<HttpResponseDto> handler(DuplicateKeyException e) {
        return responseBuilder.jsonResponseBuild(
                HttpStatus.BAD_REQUEST,
                "중복 필드 오류",
                null
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HttpResponseDto> handler(DataIntegrityViolationException e) {
        log.info(e.getMessage());
        return responseBuilder.jsonResponseBuild(
                HttpStatus.BAD_REQUEST,
                "필드 Integrity 오류",
                null
        );
    }

    @ExceptionHandler(AuthorityException.class)
    public ResponseEntity<HttpResponseDto> handler(AuthenticationException e) {
        return responseBuilder.jsonResponseBuild(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<HttpResponseDto> handler(IllegalArgumentException e) {
        return responseBuilder.jsonResponseBuild(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }
}
