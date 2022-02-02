package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDeleteDto {

    private String id;

    @Builder
    public ProductDeleteDto(String id) {
        this.id = id;
    }
}
