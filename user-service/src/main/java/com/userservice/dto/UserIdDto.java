package com.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserIdDto {
    private String id;

    @Builder
    public UserIdDto(String id) {
        this.id = id;
    }
}
