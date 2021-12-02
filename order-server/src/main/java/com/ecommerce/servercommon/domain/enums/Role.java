package com.ecommerce.servercommon.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"), SELLER("SELLER");

    private final String key;
}
