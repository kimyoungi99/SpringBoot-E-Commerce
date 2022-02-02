package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class UserJoinDto {

    private String email;

    private String password;

    private String address;

    private String birthdateString;

    private Long point;

    @Builder
    public UserJoinDto(String email, String password, String address, String birthdateString, Long point) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.birthdateString = birthdateString;
        this.point = point;
    }
}
