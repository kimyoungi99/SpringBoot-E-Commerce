package com.stockservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockEntity {

    private Long id;

    private String productId;

    private Long stock;

    @Builder
    public StockEntity(Long id, String productId, Long stock) {
        this.id = id;
        this.productId = productId;
        this.stock = stock;
    }

    public StockEntity() {

    }
}
