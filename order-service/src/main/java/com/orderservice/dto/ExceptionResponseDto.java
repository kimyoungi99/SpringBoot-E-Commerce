package com.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponseDto {
    private String name;

    private String message;

    @Builder
    public ExceptionResponseDto(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
