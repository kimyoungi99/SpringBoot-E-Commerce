package com.userservice.mapper;

import com.userservice.dto.UserJoinDto;
import com.userservice.exception.WrongDateFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserJoinDtoToUserEntityMapperTest {

    @Test
    @DisplayName("WrongDataFormatException 테스트")
    public void wrongDataFormatExceptionTest() {
        assertThrows(WrongDateFormatException.class, () -> {
            UserJoinDtoToUserEntityMapper.map(UserJoinDto.builder()
                    .birthdateString("990318")
                    .build());
        });
    }
}