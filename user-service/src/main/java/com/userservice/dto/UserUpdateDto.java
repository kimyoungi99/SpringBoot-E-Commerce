package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateDto {
    private String id;

    private String email;

    private String address;

    private String birthdateString;

    @Builder
    public UserUpdateDto(String id, String email, String address, String birthdateString) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.birthdateString = birthdateString;
    }
}
