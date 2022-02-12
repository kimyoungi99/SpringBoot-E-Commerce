package com.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderAddDto {
    private String productId;

    private Long quantity;

    private String buyerId;

    private Long moneyPayed;

    private Long pointPayed;

    private String address;

    @Builder

    public OrderAddDto(String productId, Long quantity, String buyerId, Long moneyPayed, Long pointPayed, String address) {
        this.productId = productId;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.moneyPayed = moneyPayed;
        this.pointPayed = pointPayed;
        this.address = address;
    }
}
