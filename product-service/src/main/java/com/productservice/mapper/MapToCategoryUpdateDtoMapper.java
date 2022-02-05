package com.productservice.mapper;

import com.productservice.dto.CategoryResponseDto;
import com.productservice.dto.CategoryUpdateDto;

import java.util.Map;

public class MapToCategoryUpdateDtoMapper {

    public static CategoryUpdateDto map(Map<String, String> map) {
        return CategoryUpdateDto.builder()
                .id(map.get("id"))
                .name(map.get("name"))
                .build();
    }
}
