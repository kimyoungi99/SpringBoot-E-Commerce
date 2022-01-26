package com.userservice.domain;

import com.userservice.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "users")
@Getter
public class UserEntity {

    @Id
    private String email;

    private String password;

    private String address;

    private String birthdate;

    private Long point;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdDate;

    @Builder
    public UserEntity(String email, String password, String address, String birthdate, Long point, Date createdDate) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
        this.point = point;
        this.createdDate = createdDate;
    }

    public UserDto toDto() {
        return UserDto.builder()
                .email(email)
                .password(password)
                .address(address)
                .birthdate(birthdate)
                .point(point)
                .createdDate(createdDate)
                .build();
    }
}
