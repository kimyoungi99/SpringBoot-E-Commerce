package com.productservice.mapper;

import com.productservice.dto.EmailUpdateDto;
import com.productservice.dto.ResponseDto;
import com.productservice.exception.DataException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MapToResponseDtoMapper {

    public static ResponseDto map(Map<String, Object> map) {
        return ResponseDto.builder()
                .status(Arrays.stream(HttpStatus.values())
                        .filter(httpStatus -> httpStatus.name().equals((String) map.get("status")))
                        .findFirst()
                        .orElseThrow(() -> new DataException("내부 HttpStatus 코드 오류."))
                )
                .message((String) map.get("message"))
                .dateTime(LocalDateTime.parse((String) map.get("dateTime")))
                .data(map.get("data"))
                .build();
    }
}
