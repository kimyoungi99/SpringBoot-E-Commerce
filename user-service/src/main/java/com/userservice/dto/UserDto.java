package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserDto {

    private String email;

    private String password;

    private String address;

    private String birthdate;

    private Long point;

    private Date createdDate;

    @Builder
    public UserDto(String email, String password, String address, String birthdate, Long point, Date createdDate) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
        this.point = point;
        this.createdDate = createdDate;
    }
}
