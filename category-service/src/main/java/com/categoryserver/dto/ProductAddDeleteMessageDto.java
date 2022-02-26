package com.categoryserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAddDeleteMessageDto {

    private String id;

    private String categoryId;

    @Builder
    public ProductAddDeleteMessageDto(String id, String categoryId) {
        this.id = id;
        this.categoryId = categoryId;
    }
}
