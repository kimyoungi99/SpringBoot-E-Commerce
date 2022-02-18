package com.userservice.service;

import com.userservice.common.security.JwtTokenProvider;
import com.userservice.dto.*;
import com.userservice.exception.IncorrectPasswordException;
import com.userservice.exception.InvalidTokenException;
import com.userservice.exception.KafkaConnectionException;
import com.userservice.mapper.UserJoinDtoToUserEntityMapper;
import com.userservice.dao.UserDao;
import com.userservice.domain.UserEntity;
import com.userservice.exception.UserNotExistingException;
import com.userservice.mapper.UserUpdateDtoToUserEntityMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void join(UserJoinDto userJoinDto) {
        UserEntity userEntity = UserJoinDtoToUserEntityMapper.map(userJoinDto);

        // set account created date
        userEntity.setCreatedDate(new Date());

        this.userDao.insert(userEntity);
    }

    @Transactional
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

    public EmailResponseDto getEmail(String id) {
        Optional<UserEntity> optionalUserEntity = this.userDao.findById(id);

        UserEntity userEntity =
                optionalUserEntity.orElseThrow(
                        () -> new UserNotExistingException("존재하는 유저가 없습니다.")
                );

        return EmailResponseDto.builder()
                .email(userEntity.getEmail())
                .build();
    }

    public UserLoginResultDto login(UserLoginDto userLoginDto) {
        Optional<UserEntity> optionalUserEntity = this.userDao.findByEmail(userLoginDto.getEmail());

        UserEntity userEntity =
                optionalUserEntity.orElseThrow(
                        () -> new UserNotExistingException("존재하는 유저가 없습니다.")
                );

        if(!userEntity.getPassword().equals(userLoginDto.getPassword()))
            throw new IncorrectPasswordException("비밀번호가 일치하지 않습니다.");

        String token = this.jwtTokenProvider.buildToken(userEntity.getId());

        return UserLoginResultDto.builder()
                .token(token)
                .build();
    }

    public String validate(TokenDto tokenDto) {
        String token = tokenDto.getToken();
        if (this.jwtTokenProvider.validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey("asdfasdfasdf").parseClaimsJws(token).getBody();
            String userId = (String) claims.get("id");

            this.userDao.findById(userId).orElseThrow(
                    () -> new UserNotExistingException("존재하지 않는 유저 오류")
            );

            return userId;
        }
        throw new InvalidTokenException("유효하지 않은 토큰 오류.");
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
