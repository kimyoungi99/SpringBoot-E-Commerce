package com.categoryserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryUpdateDto {
    private String id;

    private String name;

    @Builder
    public CategoryUpdateDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
