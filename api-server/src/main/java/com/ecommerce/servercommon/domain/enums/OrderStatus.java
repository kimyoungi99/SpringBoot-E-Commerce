package com.ecommerce.servercommon.domain.enums;

import lombok.Getter;

public enum OrderStatus {
    PAYED(1), CANCLED(2), REFUNDED(3), SHIPPING(4), COMPLETE(5);

    @Getter
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public static OrderStatus valueOf(int value) {
        switch (value) {
            case 1:
                return OrderStatus.PAYED;
            case 2:
                return OrderStatus.CANCLED;
            case 3:
                return OrderStatus.REFUNDED;
            case 4:
                return OrderStatus.SHIPPING;
            case 5:
                return OrderStatus.COMPLETE;
            default:
                throw new AssertionError("Unknown 'OrderStatus' Value: " + value);
        }
    }
}
