package com.userservice.controller;

import com.userservice.dao.UserDao;
import com.userservice.domain.UserEntity;
import com.userservice.dto.ResponseDto;
import com.userservice.dto.UserDeleteDto;
import com.userservice.dto.UserJoinDto;
import com.userservice.dto.UserResponseDto;
import com.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserDao userDao;

    private UserController userController;

    // Test Data
    private UserEntity userEntity1;

    @BeforeEach
    public void init() {
        this.userEntity1 = UserEntity.builder()
                .email("kimyoungi99@naver9.com")
                .password("Asdf")
                .address("seoul, korea")
                .birthdate(LocalDate.parse(
                        "1999-03-18",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ))
                .point(0L)
                .createdDate(new Date())
                .build();
        MockitoAnnotations.openMocks(this);
        this.userController = new UserController(new UserService(this.userDao));
    }

    @Test
    @DisplayName("join 테스트")
    public void joinTest() {
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor
                = ArgumentCaptor.forClass(UserEntity.class);

        this.userController.join(UserJoinDto.builder()
                .email("kimyoungi99@naver9.com")
                .password("Asdf")
                .address("seoul, korea")
                .birthdateString("1999-03-18")
                .point(0L)
                .build()
        );
        Mockito.verify(this.userDao).insert(userEntityArgumentCaptor.capture());

        checkSameUserEntityWithoutCreatedTime(this.userEntity1, userEntityArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("delete 테스트")
    public void deleteTest() {
        ArgumentCaptor<String> stringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);

        this.userController.delete(UserDeleteDto.builder()
                .email(this.userEntity1.getEmail())
                .build()
        );

        Mockito.verify(this.userDao).deleteByEmail(stringArgumentCaptor.capture());

        assertThat(this.userEntity1.getEmail()).isEqualTo(stringArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("info 테스트")
    public void infoTest() {
        Mockito.when(this.userDao.findByEmail(this.userEntity1.getEmail())).thenReturn(Optional.ofNullable(this.userEntity1));

        ResponseEntity<ResponseDto> response = this.userController.info(this.userEntity1.getEmail());

        CheckSameUserResponseDto((UserResponseDto) response.getBody().getData(), this.userEntity1.toResponseDto());
    }

    private void CheckSameUserResponseDto(UserResponseDto userResponseDto1, UserResponseDto userResponseDto2) {
        assertThat(userResponseDto1.getId()).isEqualTo(userResponseDto2.getId());
        assertThat(userResponseDto1.getEmail()).isEqualTo(userResponseDto2.getEmail());
        assertThat(userResponseDto1.getAddress()).isEqualTo(userResponseDto2.getAddress());
        assertThat(userResponseDto1.getBirthdate()).isEqualTo(userResponseDto2.getBirthdate());
        assertThat(userResponseDto1.getPoint()).isEqualTo(userResponseDto2.getPoint());
        assertThat(userResponseDto1.getCreatedDate()).isEqualTo(userResponseDto2.getCreatedDate());
    }

    private void checkSameUserEntityWithoutCreatedTime(UserEntity userEntity1, UserEntity userEntity2) {
        assertThat(userEntity1.getId()).isEqualTo(userEntity2.getId());
        assertThat(userEntity1.getEmail()).isEqualTo(userEntity2.getEmail());
        assertThat(userEntity1.getPassword()).isEqualTo(userEntity2.getPassword());
        assertThat(userEntity1.getAddress()).isEqualTo(userEntity2.getAddress());
        assertThat(userEntity1.getBirthdate()).isEqualTo(userEntity2.getBirthdate());
        assertThat(userEntity1.getPoint()).isEqualTo(userEntity2.getPoint());
    }
}