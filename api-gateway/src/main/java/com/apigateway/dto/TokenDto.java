package com.apigateway.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    private String token;

    @Builder
    public TokenDto(String token) {
        this.token = token;
    }

    public TokenDto() {
    }
}
