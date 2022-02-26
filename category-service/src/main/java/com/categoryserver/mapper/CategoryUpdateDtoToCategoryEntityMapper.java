package com.categoryserver.mapper;

import com.categoryserver.domain.CategoryEntity;
import com.categoryserver.dto.CategoryUpdateDto;

public class CategoryUpdateDtoToCategoryEntityMapper {
    public static CategoryEntity map(CategoryUpdateDto categoryUpdateDto) {
        return CategoryEntity.builder()
                .name(categoryUpdateDto.getName())
                .build();
    }
}
