package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAddDto {

    private String sellerId;

    private String sellerEmail;

    private String name;

    private Long stock;

    private Long price;

    private String categoryId;

    private String categoryName;

    @Builder
    public ProductAddDto(String sellerId, String sellerEmail, String name, Long stock, Long price, String categoryId, String categoryName) {
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
