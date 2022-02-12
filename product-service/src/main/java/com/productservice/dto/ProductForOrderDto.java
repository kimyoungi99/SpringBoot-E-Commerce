package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductForOrderDto {
    private String id;

    private String sellerId;

    private String name;

    private Long price;

    @Builder
    public ProductForOrderDto(String id, String sellerId, String name, Long price) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.price = price;
    }
}

