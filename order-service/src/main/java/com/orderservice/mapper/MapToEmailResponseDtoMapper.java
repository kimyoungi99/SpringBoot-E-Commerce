package com.orderservice.mapper;

import com.orderservice.dto.EmailResponseDto;

import java.util.Map;

public class MapToEmailResponseDtoMapper {

    public static EmailResponseDto map(Map<String, Object> map) {
        return EmailResponseDto.builder()
                .email((String) map.get("email"))
                .build();
    }
}
