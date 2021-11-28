package com.ecommerce.servercommon.dto;

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
        return new Order(null, this.productId, this.buyerId, this.orderTime, this.address, this.quantity, null);
    }
}
