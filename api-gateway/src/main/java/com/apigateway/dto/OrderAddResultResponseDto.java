package com.apigateway.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderAddResultResponseDto {
    private String result;

    private String message;

    @Builder
    public OrderAddResultResponseDto(String result, String message) {
        this.result = result;
        this.message = message;
    }
}
