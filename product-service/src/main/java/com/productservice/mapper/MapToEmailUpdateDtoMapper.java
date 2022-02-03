package com.productservice.mapper;

import com.productservice.dto.EmailUpdateDto;

import java.util.Map;

public class MapToEmailUpdateDtoMapper {

    public static EmailUpdateDto map(Map<String, String> map) {
        return EmailUpdateDto.builder()
                .id(map.get("id"))
                .email(map.get("email"))
                .build();
    }
}
