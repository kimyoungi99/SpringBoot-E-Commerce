package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserResponseDto {
    private String email;

    private String address;

    private String birthdate;

    private Long point;

    private Date createdDate;

    @Builder
    public UserResponseDto(String email, String address, String birthdate, Long point, Date createdDate) {
        this.email = email;
        this.address = address;
        this.birthdate = birthdate;
        this.point = point;
        this.createdDate = createdDate;
    }
}
