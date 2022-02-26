package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class UserLoginDto {

    private String email;

    private String password;


    @Builder
    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
