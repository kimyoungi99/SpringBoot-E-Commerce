package com.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailResponseDto {
    private String email;

    @Builder
    public EmailResponseDto(String email) {
        this.email = email;
    }
}
