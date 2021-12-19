package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.order.Order;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDto {
    private Long id;
    private Long productId;
    private Long buyerId;
    private LocalDateTime orderTime;
    private String address;
    private Integer quantity;
    private OrderStatus orderStatus;
    private Long pay;
    private Long usePoint;
}
