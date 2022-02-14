package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSimpleResponseDto {
    private String id;

    private String name;

    private Long price;

    private String categoryId;

    private String categoryName;

    @Builder
    public ProductSimpleResponseDto(String id, String name, Long price, String categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
