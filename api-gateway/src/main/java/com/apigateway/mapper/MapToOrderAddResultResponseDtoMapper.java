package com.apigateway.mapper;

import com.apigateway.dto.OrderAddResultResponseDto;
import com.apigateway.dto.ResponseDto;
import com.apigateway.exception.DataException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public class MapToOrderAddResultResponseDtoMapper {

    public static OrderAddResultResponseDto map(Map<String, Object> map) {
        return OrderAddResultResponseDto.builder()
                .result((String) map.get("result"))
                .message((String) map.get("message"))
                .build();
    }
}
