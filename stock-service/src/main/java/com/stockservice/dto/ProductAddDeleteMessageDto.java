package com.stockservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAddDeleteMessageDto {

    private String id;

    private Long stock;

    @Builder
    public ProductAddDeleteMessageDto(String id, Long stock) {
        this.id = id;
        this.stock = stock;
    }
}
