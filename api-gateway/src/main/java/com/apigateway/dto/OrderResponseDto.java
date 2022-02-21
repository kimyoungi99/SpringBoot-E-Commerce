package com.apigateway.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private String id;

    private String productId;

    private String productName;

    private Long quantity;

    private String sellerId;

    private String sellerEmail;

    private String buyerId;

    private Long moneyPayed;

    private String address;

    private Long pointPayed;

    private LocalDateTime orderTime;

    @Builder
    public OrderResponseDto(String id, String productId, String productName, Long quantity, String sellerId, String sellerEmail, String buyerId, Long moneyPayed, String address, Long pointPayed, LocalDateTime orderTime) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.buyerId = buyerId;
        this.moneyPayed = moneyPayed;
        this.address = address;
        this.pointPayed = pointPayed;
        this.orderTime = orderTime;
    }
}
