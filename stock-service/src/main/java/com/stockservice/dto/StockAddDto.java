package com.stockservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockAddDto {
    private String productId;

    private Long stock;

    @Builder
    public StockAddDto(String productId, Long stock) {
        this.productId = productId;
        this.stock = stock;
    }
}
