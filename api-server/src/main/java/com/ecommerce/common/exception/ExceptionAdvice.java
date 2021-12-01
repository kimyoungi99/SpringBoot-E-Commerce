package com.ecommerce.common.exception;

import com.ecommerce.common.response.HttpResponseDto;
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

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<HttpResponseDto> handler(DuplicateKeyException e) {
        HttpResponseDto httpResponseDto = new HttpResponseDto();
        httpResponseDto.setMessage("중복 필드 오류");
        httpResponseDto.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(httpResponseDto, getHttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HttpResponseDto> handler(DataIntegrityViolationException e) {
        HttpResponseDto httpResponseDto = new HttpResponseDto();
        httpResponseDto.setMessage("필드 Integrity 오류");
        httpResponseDto.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(httpResponseDto, getHttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HttpResponseDto> handler(AuthenticationException e) {
        HttpResponseDto httpResponseDto = new HttpResponseDto();
        httpResponseDto.setMessage(e.getMessage());
        httpResponseDto.setStatus(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(httpResponseDto, getHttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return headers;
    }
}
