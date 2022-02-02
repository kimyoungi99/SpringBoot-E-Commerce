package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDeleteDto {
    private String email;

    @Builder
    public UserDeleteDto(String email) {
        this.email = email;
    }
}
