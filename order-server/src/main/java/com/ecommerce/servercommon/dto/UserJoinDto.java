package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.enums.Role;
import com.ecommerce.servercommon.domain.user.User;
import lombok.Data;

@Data
public class UserJoinDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String role;
    private Long point;

    public User toEntity() {
        return User.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .address(this.address)
                .point(this.point)
                .role(Role.valueOf(this.role))
                .build();
    }
}
