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
    private Long pay;
    private Long usePoint;

    public Order toEntityWithOrderStatus(OrderStatus orderStatus) {
        return Order.builder()
                .productId(this.productId)
                .buyerId(this.buyerId)
                .orderStatus(orderStatus)
                .orderTime(this.orderTime)
                .address(this.address)
                .quantity(this.quantity)
                .pay(this.pay)
                .usePoint(this.usePoint)
                .build();
    }
}
