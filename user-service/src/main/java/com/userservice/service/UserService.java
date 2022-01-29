package com.userservice.service;

import com.userservice.mapper.UserJoinDtoToUserEntityMapper;
import com.userservice.dao.UserDao;
import com.userservice.domain.UserEntity;
import com.userservice.dto.UserDeleteDto;
import com.userservice.dto.UserJoinDto;
import com.userservice.dto.UserResponseDto;
import com.userservice.exception.UserNotExistingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

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
}
