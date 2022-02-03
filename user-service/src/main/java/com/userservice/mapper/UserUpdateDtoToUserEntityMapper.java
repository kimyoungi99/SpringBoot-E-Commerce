package com.userservice.mapper;

import com.userservice.domain.UserEntity;
import com.userservice.dto.UserUpdateDto;
import com.userservice.exception.WrongDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserUpdateDtoToUserEntityMapper {

    public static UserEntity map(UserUpdateDto userUpdateDto) {
        LocalDate birthdate = null;
        if(userUpdateDto.getBirthdateString() != null) {
            try {
                birthdate = LocalDate.parse(userUpdateDto.getBirthdateString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new WrongDateFormatException("생일 날짜 형식 오류.");
            }
        }

        return UserEntity.builder()
                .id(userUpdateDto.getId())
                .email(userUpdateDto.getEmail())
                .address(userUpdateDto.getAddress())
                .birthdate(birthdate)
                .build();
    }
}
