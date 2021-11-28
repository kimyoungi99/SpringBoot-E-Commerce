package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.order.Order;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderDto implements Serializable {
    private Long productId;
    private Long buyerId;
    private LocalDateTime orderTime;
    private String address;
    private Integer quantity;

    public Order toEntity() {
        return Order.builder()
                .productId(this.productId)
                .buyerId(this.buyerId)
                .orderStatus(OrderStatus.PAYED)
                .orderTime(this.orderTime)
                .address(this.address)
                .quantity(this.quantity)
                .build();
    }
}
