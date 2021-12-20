package com.ecommerce.servercommon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Long point;
}
