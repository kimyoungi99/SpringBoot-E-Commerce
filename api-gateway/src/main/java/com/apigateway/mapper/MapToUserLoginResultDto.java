package com.apigateway.mapper;

import com.apigateway.dto.UserLoginResultDto;

import java.util.Map;

public class MapToUserLoginResultDto {
    public static UserLoginResultDto map(Map<String, Object> map) {
        return UserLoginResultDto.builder()
                .token((String) map.get("token"))
                .build();
    }
}
