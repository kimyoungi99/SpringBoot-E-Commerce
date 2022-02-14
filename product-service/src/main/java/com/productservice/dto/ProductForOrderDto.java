package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductForOrderDto {
    private String id;

    private String sellerId;

    private String sellerEmail;

    private String name;

    private Long price;

    @Builder
    public ProductForOrderDto(String id, String sellerId, String sellerEmail, String name, Long price) {
        this.id = id;
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.name = name;
        this.price = price;
    }
}

