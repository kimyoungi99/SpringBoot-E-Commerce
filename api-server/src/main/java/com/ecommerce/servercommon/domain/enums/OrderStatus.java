package com.ecommerce.servercommon.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PAYED(1), REFUNDED(2), COMPLETE(3);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public static OrderStatus valueOf(int value) {
        switch (value) {
            case 1:
                return OrderStatus.PAYED;
            case 2:
                return OrderStatus.REFUNDED;
            case 3:
                return OrderStatus.COMPLETE;
            default:
                throw new AssertionError("Unknown 'OrderStatus' Value: " + value);
        }
    }
}
