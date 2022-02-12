package com.stockservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockCheckDto {
    private String productId;
    private Long quantity;

    @Builder
    public StockCheckDto(String productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
