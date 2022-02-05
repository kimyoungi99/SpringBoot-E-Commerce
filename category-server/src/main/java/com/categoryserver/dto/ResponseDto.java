package com.categoryserver.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ResponseDto {
    private HttpStatus status;

    private String message;

    private LocalDateTime dateTime;

    private Object data;

    @Builder
    public ResponseDto(HttpStatus status, String message, LocalDateTime dateTime, Object data) {
        this.status = status;
        this.message = message;
        this.dateTime = dateTime;
        this.data = data;
    }
}
