package com.ecommerce.servercommon.domain.order;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@AllArgsConstructor
public class Order {
    private Long id;
    private Long productId;
    private LocalDateTime orderTime;
    private String address;
    private Integer quantity;
    private OrderStatus orderStatus;
}
