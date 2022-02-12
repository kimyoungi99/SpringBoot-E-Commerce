package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAddDto {

    private String sellerId;

    private String name;

    private Long stock;

    private Long price;

    private String categoryId;

    @Builder
    public ProductAddDto(String sellerId, String name, Long stock, Long price, String categoryId) {
        this.sellerId = sellerId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.categoryId = categoryId;
    }
}
