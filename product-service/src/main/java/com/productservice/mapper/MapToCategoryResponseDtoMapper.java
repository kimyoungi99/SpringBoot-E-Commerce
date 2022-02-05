package com.productservice.mapper;

import com.productservice.dto.CategoryResponseDto;

import java.util.Map;

public class MapToCategoryResponseDtoMapper {

    public static CategoryResponseDto map(Map<String, Object> map) {
        return CategoryResponseDto.builder()
                .name((String) map.get("name"))
                .build();
    }
}
