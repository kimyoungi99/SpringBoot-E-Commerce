package com.userservice.mapper;

import com.userservice.domain.UserEntity;
import com.userservice.dto.UserJoinDto;
import com.userservice.exception.WrongDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserJoinDtoToUserEntityMapper {

    public static UserEntity map(UserJoinDto userJoinDto) {
        LocalDate birthdate;
        try {
            birthdate = LocalDate.parse(userJoinDto.getBirthdateString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new WrongDateFormatException("생일 날짜 형식 오류.");
        }

        return UserEntity.builder()
                .email(userJoinDto.getEmail())
                .password(userJoinDto.getPassword())
                .address(userJoinDto.getAddress())
                .birthdate(birthdate)
                .createdDate(null)
                .point(userJoinDto.getPoint())
                .build();
    }
}
