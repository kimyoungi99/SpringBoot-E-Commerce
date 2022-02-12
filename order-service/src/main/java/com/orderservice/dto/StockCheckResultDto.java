package com.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockCheckResultDto {
    private boolean result;

    @Builder
    public StockCheckResultDto(boolean result) {
        this.result = result;
    }
}
