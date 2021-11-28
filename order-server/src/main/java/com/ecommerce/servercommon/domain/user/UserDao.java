package com.ecommerce.servercommon.domain.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    void add(User user);

    User findById(Long id);

    User findByEmail(String Email);

    void deleteById(Long id);
}
