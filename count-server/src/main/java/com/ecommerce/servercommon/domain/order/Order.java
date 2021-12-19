package com.ecommerce.servercommon.domain.order;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.dto.OrderResponseDto;
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
    private Long buyerId;
    private LocalDateTime orderTime;
    private String address;
    private Integer quantity;
    private OrderStatus orderStatus;
    private Long pay;
    private Long usePoint;

    public OrderResponseDto toResponseDto() {
        return OrderResponseDto.builder()
                .id(id)
                .productId(this.productId)
                .buyerId(this.buyerId)
                .orderTime(this.orderTime)
                .address(this.address)
                .quantity(this.quantity)
                .orderStatus(this.orderStatus)
                .pay(this.pay)
                .usePoint(this.usePoint)
                .build();
    }
}
