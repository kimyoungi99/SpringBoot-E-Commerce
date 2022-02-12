package com.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderAddResultResponse {
    private String result;

    private String message;

    @Builder
    public OrderAddResultResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }
}
