package com.productservice.mapper;

import com.productservice.dto.StockUpdateDto;

import java.util.Map;

public class MapToStockUpdateDtoMapper {
    public static StockUpdateDto map(Map<String, Object> map) {
        return StockUpdateDto.builder()
                .productId((String) map.get("productId"))
                .quantity((Long) map.get("quantity"))
                .build();
    }
}
