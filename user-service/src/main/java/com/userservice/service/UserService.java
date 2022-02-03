package com.userservice.service;

import com.userservice.dto.*;
import com.userservice.exception.KafkaConnectionException;
import com.userservice.mapper.UserJoinDtoToUserEntityMapper;
import com.userservice.dao.UserDao;
import com.userservice.domain.UserEntity;
import com.userservice.exception.UserNotExistingException;
import com.userservice.mapper.UserUpdateDtoToUserEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;
    private final UserDao userDao;
    private final String userUpdateTopicName;

    public void join(UserJoinDto userJoinDto) {
        UserEntity userEntity = UserJoinDtoToUserEntityMapper.map(userJoinDto);

        // set account created date
        userEntity.setCreatedDate(new Date());

        this.userDao.insert(userEntity);
    }

    public void delete(UserDeleteDto userDeleteDto) {
        this.userDao.deleteByEmail(userDeleteDto.getEmail());
    }

    public UserResponseDto info(String email) {
        Optional<UserEntity> optionalUserEntity = this.userDao.findByEmail(email);

        UserEntity userEntity =
                optionalUserEntity.orElseThrow(
                        () -> new UserNotExistingException("존재하는 유저가 없습니다.")
                );

        return userEntity.toResponseDto();
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto) {
        Optional<UserEntity> optionalUserEntity = this.userDao.findById(userUpdateDto.getId());

        UserEntity userEntity =
                optionalUserEntity.orElseThrow(
                        () -> new UserNotExistingException("존재하는 유저가 없습니다.")
                );

        this.userDao.update(UserUpdateDtoToUserEntityMapper.map(userUpdateDto));

        // 이메일 수정 시 전파
        if(userUpdateDto.getEmail() != null & !userUpdateDto.getEmail().equals(userEntity.getEmail())) {
            KafkaMessageDto kafkaMessageDto = KafkaMessageDto.builder()
                    .domain("UserService")
                    .eventType("EmailUpdateEvent")
                    .data(EmailUpdateDto.builder()
                            .id(userUpdateDto.getId())
                            .email(userUpdateDto.getEmail())
                            .build()
                    )
                    .build();
            try {
                this.kafkaTemplate.send(this.userUpdateTopicName, kafkaMessageDto);
            }
            catch (Exception e) {
                log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
                throw new KafkaConnectionException("카프카 응답 오류.");
            }
        }
    }
}
