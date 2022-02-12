package com.userservice.dao;

import com.userservice.domain.UserEntity;

import java.util.Optional;

public interface UserDao {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(String id);

    String insert(UserEntity userEntity);

    void update(UserEntity userEntity);

    void deleteByEmail(String email);

    void deleteById(String id);
}
