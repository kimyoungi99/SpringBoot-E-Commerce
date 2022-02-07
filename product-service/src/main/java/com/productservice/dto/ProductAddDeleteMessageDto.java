package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAddDeleteMessageDto {

    private String id;

    private String sellerId;

    private String name;

    private Long stock;

    private Long price;

    private String categoryId;

    @Builder
    public ProductAddDeleteMessageDto(String id, String sellerId, String name, Long stock, Long price, String categoryId) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.categoryId = categoryId;
    }
}
