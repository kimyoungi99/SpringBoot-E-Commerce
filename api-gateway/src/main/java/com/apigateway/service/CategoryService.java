package com.apigateway.service;

import com.apigateway.client.CategoryServiceClient;
import com.apigateway.dto.CategoryAddDto;
import com.apigateway.dto.CategoryResponseDto;
import com.apigateway.dto.CategoryUpdateDto;
import com.apigateway.dto.ResponseDto;
import com.apigateway.exception.CategoryServiceConnectionException;
import com.apigateway.exception.FeignClientException;
import com.apigateway.mapper.MapToResponseDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryServiceClient categoryServiceClient;

    public ResponseDto getAll() {
        ResponseDto categoryListResponse = null;
        try {
            categoryListResponse = MapToResponseDtoMapper.map(this.categoryServiceClient.getAll());
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if (e instanceof FeignClientException)
                throw e;
            throw new CategoryServiceConnectionException("카테고리 서비스 응답 오류.");
        }
        List<CategoryResponseDto> categoryResponseDtoList = (List<CategoryResponseDto>) categoryListResponse.getData();
        return ResponseDto.builder()
                .data(categoryResponseDtoList)
                .dateTime(LocalDateTime.now())
                .message(categoryListResponse.getMessage())
                .status(categoryListResponse.getStatus())
                .build();
    }

    public ResponseDto update(CategoryUpdateDto categoryUpdateDto) {
        ResponseDto categoryUpdateResponse = null;
        try {
            categoryUpdateResponse = MapToResponseDtoMapper.map(this.categoryServiceClient.update(categoryUpdateDto));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if (e instanceof FeignClientException)
                throw e;
            throw new CategoryServiceConnectionException("카테고리 서비스 응답 오류.");
        }
        return ResponseDto.builder()
                .dateTime(LocalDateTime.now())
                .message(categoryUpdateResponse.getMessage())
                .status(categoryUpdateResponse.getStatus())
                .build();
    }

    public ResponseDto add(CategoryAddDto categoryAddDto) {
        ResponseDto categoryUpdateResponse = null;
        try {
            categoryUpdateResponse = MapToResponseDtoMapper.map(this.categoryServiceClient.add(categoryAddDto));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if (e instanceof FeignClientException)
                throw e;
            throw new CategoryServiceConnectionException("카테고리 서비스 응답 오류.");
        }
        return ResponseDto.builder()
                .dateTime(LocalDateTime.now())
                .message(categoryUpdateResponse.getMessage())
                .status(categoryUpdateResponse.getStatus())
                .build();
    }
}
