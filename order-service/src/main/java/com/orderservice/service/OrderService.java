package com.orderservice.service;

import com.orderservice.client.ProductServiceClient;
import com.orderservice.client.StockServiceClient;
import com.orderservice.client.UserServiceClient;
import com.orderservice.dao.OrderDao;
import com.orderservice.domain.OrderEntity;
import com.orderservice.dto.*;
import com.orderservice.exception.ProductServiceConnectionException;
import com.orderservice.exception.StockServiceConnectionException;
import com.orderservice.mapper.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@EnableFeignClients(basePackages = "com.orderservice.client")
public class OrderService {

    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;
    private final StockServiceClient stockServiceClient;
    private final OrderDao orderDao;

    public OrderAddResultResponse order(OrderAddDto orderAddDto) {
        OrderEntity orderEntity = OrderAddDtoToOrderEntityMapper.map(orderAddDto);

        // init
        ResponseDto productResponse = null;
        try {
            productResponse = MapToResponseDtoMapper.map(
                    this.productServiceClient.getProduct(orderEntity.getProductId())
            );
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new ProductServiceConnectionException("상품 서비스 응답 오류.");
        }
        ProductForOrderDto productForOrderDto = MapToProductForOrderDtoMapper.map((Map<String, Object>) productResponse.getData());
        if (productForOrderDto.getPrice() != orderAddDto.getMoneyPayed() + orderAddDto.getPointPayed()) {
            return OrderAddResultResponse.builder()
                    .result("fail")
                    .message("상품 금액과 지불한 금액이 일치하지 않습니다.")
                    .build();
        }

        ResponseDto stockCheckResponse = null;
        try {
            stockCheckResponse = MapToResponseDtoMapper.map(
                    this.stockServiceClient.check(orderAddDto.getProductId(), orderAddDto.getQuantity())
            );
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new StockServiceConnectionException("수량 서비스 응답 오류.");
        }
        if (!MapToStockCheckResultDtoMapper.map((Map<String, Object>) stockCheckResponse.getData()).isResult()) {
            return OrderAddResultResponse.builder()
                    .result("fail")
                    .message("상품 수량이 부족합니다.")
                    .build();
        }

        try {
            orderEntity.setProductName(productForOrderDto.getName());
            orderEntity.setOrderTime(LocalDateTime.now());
            orderEntity.setSellerEmail(productForOrderDto.getSellerEmail());

            this.orderDao.insert(orderEntity);
        } catch (Exception e) {
            this.stockServiceClient.update(orderAddDto.getProductId(), orderAddDto.getQuantity());
        }

        return OrderAddResultResponse.builder()
                .result("success")
                .message("주문 성공.")
                .build();
    }

    @Builder

    public OrderService(ProductServiceClient productServiceClient, UserServiceClient userServiceClient, StockServiceClient stockServiceClient, OrderDao orderDao) {
        this.productServiceClient = productServiceClient;
        this.userServiceClient = userServiceClient;
        this.stockServiceClient = stockServiceClient;
        this.orderDao = orderDao;
    }
}
