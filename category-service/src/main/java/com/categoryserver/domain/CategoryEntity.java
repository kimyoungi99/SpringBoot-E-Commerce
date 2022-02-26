package com.categoryserver.domain;

import com.categoryserver.dto.CategoryResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class CategoryEntity {

    @Id
    private String id;

    private String name;

    private Long count;

    @Builder
    public CategoryEntity(String id, String name, Long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public CategoryResponseDto toResponseDto() {
        return CategoryResponseDto.builder()
                .id(this.id)
                .name(this.name)
                .count(this.count)
                .build();
    }
}
