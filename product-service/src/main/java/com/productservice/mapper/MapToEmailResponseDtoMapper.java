package com.productservice.mapper;

import com.productservice.dto.CategoryResponseDto;
import com.productservice.dto.EmailResponseDto;

import java.util.Map;

public class MapToEmailResponseDtoMapper {

    public static EmailResponseDto map(Map<String, Object> map) {
        return EmailResponseDto.builder()
                .email((String) map.get("email"))
                .build();
    }
}
