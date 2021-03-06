package com.userservice.service;

import com.userservice.common.security.JwtTokenProvider;
import com.userservice.dao.UserDao;
import com.userservice.domain.UserEntity;
import com.userservice.dto.*;
import com.userservice.exception.UserNotExistingException;
import com.userservice.exception.WrongDateFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;

    private UserService userService;

    // Test Data
    private UserEntity userEntity1;
    private String kafkaTopicName = "asdf";

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
        this.userService = new UserService(this.kafkaTemplate, this.userDao, this.kafkaTopicName, new JwtTokenProvider());
    }

    @Test
    @DisplayName("UserService join 테스트")
    public void joinTest() {
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor
                = ArgumentCaptor.forClass(UserEntity.class);

        this.userService.join(UserJoinDto.builder()
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

        this.userService.delete(UserDeleteDto.builder()
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

        UserResponseDto userResponseDto = this.userService.info(this.userEntity1.getEmail());

        checkSameUserResponseDto(userResponseDto, this.userEntity1.toResponseDto());
    }

    @Test
    @DisplayName("유저 존재 하지 않는 경우 테스트")
    public void infoUserNotExistingExceptionTest() {
        Mockito.when(this.userDao.findByEmail("")).thenReturn(Optional.ofNullable(null));

        assertThrows(UserNotExistingException.class, () -> {
            this.userService.info("");
        });
    }

    @Test
    @DisplayName("생일 날짜 형식 오류 테스트")
    public void wrongDataFormatExceptionTest() {
        UserJoinDto wrongUserJoinDto = UserJoinDto.builder()
                .email("asdf99@naver.com")
                .birthdateString("990318")
                .build();

        assertThrows(WrongDateFormatException.class, () -> {
            this.userService.join(wrongUserJoinDto);
        });
    }

    @Test
    @DisplayName("getEmail 테스트")
    public void getEmailTest() {
        Mockito.when(this.userDao.findById(this.userEntity1.getId())).thenReturn(Optional.ofNullable(this.userEntity1));

        EmailResponseDto emailResponseDto = this.userService.getEmail(this.userEntity1.getId());

        assertThat(emailResponseDto.getEmail()).isEqualTo(this.userEntity1.getEmail());
    }

    @Test
    @DisplayName("update 테스트")
    public void updateTest() {
        String userId = "asdf";
        Mockito.when(this.userDao.findById(userId)).thenReturn(Optional.ofNullable(this.userEntity1));
        ArgumentCaptor<KafkaMessageDto> kafkaMessageDtoArgumentCaptor
                = ArgumentCaptor.forClass(KafkaMessageDto.class);
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor
                = ArgumentCaptor.forClass(UserEntity.class);
        ArgumentCaptor<String> stringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .id(userId)
                .email("newemail@naver.com")
                .address("busan, korea")
                .build();


        this.userService.update(userUpdateDto);

        Mockito.verify(this.userDao).update(userEntityArgumentCaptor.capture());
        Mockito.verify(this.kafkaTemplate).send(stringArgumentCaptor.capture(), kafkaMessageDtoArgumentCaptor.capture());

        assertThat(userUpdateDto.getId()).isEqualTo(userEntityArgumentCaptor.getValue().getId());
        assertThat(userUpdateDto.getEmail()).isEqualTo(userEntityArgumentCaptor.getValue().getEmail());
        assertThat(userUpdateDto.getAddress()).isEqualTo(userEntityArgumentCaptor.getValue().getAddress());

        assertThat(this.kafkaTopicName).isEqualTo(stringArgumentCaptor.getValue());
        assertThat(userUpdateDto.getEmail()).isEqualTo(
                ((EmailUpdateDto) kafkaMessageDtoArgumentCaptor.getValue().getData()).getEmail()
        );
    }

    private void checkSameUserResponseDto(UserResponseDto userResponseDto1, UserResponseDto userResponseDto2) {
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