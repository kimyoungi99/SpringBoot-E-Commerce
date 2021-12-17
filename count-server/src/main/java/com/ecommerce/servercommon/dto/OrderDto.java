package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.order.Order;
import lombok.Builder;
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

    public Order toEntityWithOrderStatus(OrderStatus orderStatus) {
        return Order.builder()
                .productId(this.productId)
                .buyerId(this.buyerId)
                .orderStatus(orderStatus)
                .orderTime(this.orderTime)
                .address(this.address)
                .quantity(this.quantity)
                .build();
    }
}
