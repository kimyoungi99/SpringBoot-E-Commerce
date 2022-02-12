package com.categoryserver.mapper;

import com.categoryserver.dto.ProductAddDeleteMessageDto;

import java.util.Map;

public class MapToProductAddDeleteMessageDtoMapper {
    public static ProductAddDeleteMessageDto map(Map<String, String> map) {
        return ProductAddDeleteMessageDto.builder()
                .id(map.get("id"))
                .categoryId(map.get("categoryId"))
                .build();
    }
}
