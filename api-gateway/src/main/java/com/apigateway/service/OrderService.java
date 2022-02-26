package com.apigateway.service;

import com.apigateway.client.OrderServiceClient;
import com.apigateway.client.UserServiceClient;
import com.apigateway.dto.*;
import com.apigateway.exception.FeignClientException;
import com.apigateway.exception.OrderServiceConnectionException;
import com.apigateway.exception.UserServiceConnectionException;
import com.apigateway.mapper.MapToOrderAddResultResponseDtoMapper;
import com.apigateway.mapper.MapToResponseDtoMapper;
import com.apigateway.mapper.MapToUserIdStringMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    private final OrderServiceClient orderServiceClient;
    private final UserServiceClient userServiceClient;

    public ResponseDto order(String token, OrderAddDto orderAddDto) {
        String userId = getUserId(token);

        ResponseDto orderResponse = null;
        try {
            orderResponse = MapToResponseDtoMapper.map(this.orderServiceClient.order(orderAddDto));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new OrderServiceConnectionException("주문 서비스 응답 오류.");
        }
        OrderAddResultResponseDto orderAddResultResponseDto
                = MapToOrderAddResultResponseDtoMapper.map((Map<String, Object>) orderResponse.getData());
        return ResponseDto.builder()
                .data(orderAddResultResponseDto)
                .dateTime(LocalDateTime.now())
                .message(orderResponse.getMessage())
                .status(orderResponse.getStatus())
                .build();
    }

    public ResponseDto getOrderList(String token) {
        String userId = getUserId(token);

        ResponseDto orderListResponse = null;
        try {
            orderListResponse = MapToResponseDtoMapper.map(this.orderServiceClient.getOrderList(userId));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if(e instanceof FeignClientException)
                throw e;
            throw new OrderServiceConnectionException("주문 서비스 응답 오류.");
        }
        List<OrderResponseDto> orderResponseDtoList = (List<OrderResponseDto>) orderListResponse.getData();
        return ResponseDto.builder()
                .data(orderResponseDtoList)
                .dateTime(LocalDateTime.now())
                .message(orderListResponse.getMessage())
                .status(orderListResponse.getStatus())
                .build();
    }

    public ResponseDto getSellList(String token) {
        String userId = getUserId(token);

        ResponseDto orderListResponse = null;
        try {
            orderListResponse = MapToResponseDtoMapper.map(this.orderServiceClient.getSellList(userId));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if(e instanceof FeignClientException)
                throw e;
            throw new OrderServiceConnectionException("주문 서비스 응답 오류.");
        }
        List<OrderResponseDto> orderResponseDtoList = (List<OrderResponseDto>) orderListResponse.getData();
        return ResponseDto.builder()
                .data(orderResponseDtoList)
                .dateTime(LocalDateTime.now())
                .message(orderListResponse.getMessage())
                .status(orderListResponse.getStatus())
                .build();
    }

    private String getUserId(String token) {
        ResponseDto validationResponse = null;
        try {
            validationResponse = MapToResponseDtoMapper.map(this.userServiceClient.validate(
                    TokenDto.builder()
                            .token(token)
                            .build()
            ));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if(e instanceof FeignClientException)
                throw e;
            throw new UserServiceConnectionException("유저 서비스 응답 오류.");
        }
        String userId = MapToUserIdStringMapper.map((Map<String, Object>) validationResponse.getData());
        return userId;
    }

    @Builder
    public OrderService(OrderServiceClient orderServiceClient, UserServiceClient userServiceClient) {
        this.orderServiceClient = orderServiceClient;
        this.userServiceClient = userServiceClient;
    }
}
