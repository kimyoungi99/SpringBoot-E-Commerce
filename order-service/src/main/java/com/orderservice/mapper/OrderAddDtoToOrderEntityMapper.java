package com.orderservice.mapper;

import com.orderservice.domain.OrderEntity;
import com.orderservice.dto.OrderAddDto;

public class OrderAddDtoToOrderEntityMapper {
    public static OrderEntity map(OrderAddDto orderAddDto) {
        return OrderEntity.builder()
                .productId(orderAddDto.getProductId())
                .quantity(orderAddDto.getQuantity())
                .sellerId(orderAddDto.getSellerId())
                .buyerId(orderAddDto.getBuyerId())
                .moneyPayed(orderAddDto.getMoneyPayed())
                .pointPayed(orderAddDto.getPointPayed())
                .address(orderAddDto.getAddress())
                .build();
    }
}
