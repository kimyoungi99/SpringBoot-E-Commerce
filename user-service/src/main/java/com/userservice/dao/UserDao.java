package com.userservice.dao;

import com.userservice.domain.UserEntity;

import java.util.Optional;

public interface UserDao {

    Optional<UserEntity> findByEmail(String email);

    String insert(UserEntity userEntity);

    void deleteByEmail(String email);
}
