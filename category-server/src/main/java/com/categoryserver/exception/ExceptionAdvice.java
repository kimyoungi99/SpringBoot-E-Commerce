package com.categoryserver.exception;

import com.categoryserver.dto.ExceptionResponseDto;
import com.categoryserver.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handler(Exception e) {
        return ResponseEntity
                .badRequest()
                .body(ResponseDto.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .dateTime(LocalDateTime.now())
                        .message(e.getMessage())
                        .data(ExceptionResponseDto.builder()
                                .message(e.getMessage())
                                .name(e.getClass().getSimpleName())
                                .build())
                        .build()
                );
    }
}
