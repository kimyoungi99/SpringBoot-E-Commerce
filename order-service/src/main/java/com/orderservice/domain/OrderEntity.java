package com.orderservice.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class OrderEntity {

    @Id
    private String id;

    private String productId;

    private String productName;

    private Long quantity;

    private String sellerId;

    private String sellerEmail;

    private String buyerId;

    private Long moneyPayed;

    private Long pointPayed;

    @Builder
    public OrderEntity(String id, String productId, String productName, Long quantity, String sellerId, String sellerEmail, String buyerId, Long moneyPayed, Long pointPayed) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.buyerId = buyerId;
        this.moneyPayed = moneyPayed;
        this.pointPayed = pointPayed;
    }
}
