package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockUpdateDto {
    private String productId;
    private Long quantity;

    @Builder
    public StockUpdateDto(String productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
