package com.categoryserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryAddDto {
    private String name;

    @Builder
    public CategoryAddDto(String name) {
        this.name = name;
    }

    public CategoryAddDto() {

    }
}
