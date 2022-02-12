package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailUpdateDto {
    private String id;

    private String email;

    @Builder
    public EmailUpdateDto(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
