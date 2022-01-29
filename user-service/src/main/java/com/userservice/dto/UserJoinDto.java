package com.userservice.dto;

import com.userservice.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserJoinDto {

    private String email;

    private String password;

    private String address;

    private String birthdate;

    private Long point;

    @Builder
    public UserJoinDto(String email, String password, String address, String birthdate, Long point) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
        this.point = point;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(this.email)
                .password(this.password)
                .address(this.address)
                .birthdate(this.birthdate)
                .point(this.point)
                .build();
    }
}
