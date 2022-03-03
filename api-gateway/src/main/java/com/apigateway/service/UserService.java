package com.apigateway.service;

import com.apigateway.client.UserServiceClient;
import com.apigateway.dto.*;
import com.apigateway.exception.FeignClientException;
import com.apigateway.exception.UserServiceConnectionException;
import com.apigateway.mapper.MapToResponseDtoMapper;
import com.apigateway.mapper.MapToUserLoginResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
public class UserService {

    private final UserServiceClient userServiceClient;

    public ResponseDto join(UserJoinDto userJoinDto) {
        ResponseDto joinResponse = null;
        try {
            joinResponse = MapToResponseDtoMapper.map(this.userServiceClient.join(userJoinDto));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if(e instanceof FeignClientException)
                throw e;
            throw new UserServiceConnectionException("유저 서비스 응답 오류.");
        }
        return ResponseDto.builder()
                .dateTime(LocalDateTime.now())
                .message(joinResponse.getMessage())
                .status(joinResponse.getStatus())
                .build();
    }

    public ResponseDto login(UserLoginDto userLoginDto) {
        ResponseDto loginResponse = null;
        try {
            loginResponse = MapToResponseDtoMapper.map(this.userServiceClient.login(userLoginDto));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if(e instanceof FeignClientException)
                throw e;
            throw new UserServiceConnectionException("유저 서비스 응답 오류.");
        }
        UserLoginResultDto loginResultDto = MapToUserLoginResultDto.map((Map<String, Object>) loginResponse.getData());

        return ResponseDto.builder()
                .data(loginResultDto)
                .dateTime(LocalDateTime.now())
                .message(loginResponse.getMessage())
                .status(loginResponse.getStatus())
                .build();
    }

    public ResponseDto update(UserUpdateDto userUpdateDto) {
        ResponseDto updateResponse = null;
        try {
            updateResponse = MapToResponseDtoMapper.map(this.userServiceClient.update(userUpdateDto));
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            if(e instanceof FeignClientException)
                throw e;
            throw new UserServiceConnectionException("유저 서비스 응답 오류.");
        }

        return ResponseDto.builder()
                .dateTime(LocalDateTime.now())
                .message(updateResponse.getMessage())
                .status(updateResponse.getStatus())
                .build();
    }

    public UserService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }
}
