package com.apigateway.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginResultDto {
    private String token;

    @Builder
    public UserLoginResultDto(String token) {
        this.token = token;
    }
}
