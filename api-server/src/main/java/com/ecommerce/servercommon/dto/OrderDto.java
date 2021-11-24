package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderDto implements Serializable {
    private Long productId;
    private LocalDateTime orderTime;
    private String address;
    private Integer quantity;
}
