package com.orderservice.mapper;

import com.orderservice.dto.ProductForOrderDto;

import java.util.Map;

public class MapToProductForOrderDtoMapper {

    public static ProductForOrderDto map(Map<String, Object> map) {
        return ProductForOrderDto.builder()
                .id((String) map.get("id"))
                .name((String) map.get("name"))
                .price(Long.valueOf((Integer) map.get("price")))
                .build();
    }
}
