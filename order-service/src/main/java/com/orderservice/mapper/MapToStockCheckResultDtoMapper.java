package com.orderservice.mapper;

import com.orderservice.dto.StockCheckResultDto;

import java.util.Map;

public class MapToStockCheckResultDtoMapper {
    public static StockCheckResultDto map(Map<String, Object> map) {
        return StockCheckResultDto.builder()
                .result((boolean) map.get("result"))
                .build();
    }
}
