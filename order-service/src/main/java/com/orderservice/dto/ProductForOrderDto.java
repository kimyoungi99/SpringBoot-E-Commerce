package com.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductForOrderDto {
    private String id;

    private String name;

    private Long price;

    @Builder
    public ProductForOrderDto(String id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
