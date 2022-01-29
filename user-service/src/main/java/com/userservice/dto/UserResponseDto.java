package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class UserResponseDto {
    private String id;

    private String email;

    private String address;

    private LocalDate birthdate;

    private Long point;

    private Date createdDate;

    @Builder
    public UserResponseDto(String id, String email, String address, LocalDate birthdate, Long point, Date createdDate) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.birthdate = birthdate;
        this.point = point;
        this.createdDate = createdDate;
    }
}
