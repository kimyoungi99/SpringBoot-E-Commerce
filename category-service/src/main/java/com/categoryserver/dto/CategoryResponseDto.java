package com.categoryserver.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private String id;

    private String name;

    private Long count;

    @Builder
    public CategoryResponseDto(String id, String name, Long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
