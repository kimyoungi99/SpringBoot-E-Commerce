package com.stockservice.mapper;

import com.stockservice.dto.ProductAddDeleteMessageDto;

import java.util.Map;

public class MapToProductAddDeleteMessageDtoMapper {
    public static ProductAddDeleteMessageDto map(Map<String, Object> map) {
        return ProductAddDeleteMessageDto.builder()
                .id((String) map.get("id"))
                .stock(Long.valueOf((Integer) map.get("stock")))
                .build();
    }
}
