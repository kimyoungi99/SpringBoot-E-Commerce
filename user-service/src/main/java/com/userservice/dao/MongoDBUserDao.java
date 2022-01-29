package com.userservice.dao;

import com.mongodb.MongoWriteException;
import com.userservice.domain.UserEntity;
import com.userservice.exception.DataResponseException;
import com.userservice.exception.DuplicateEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
public class MongoDBUserDao implements UserDao {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        Query findByEmailQuery = new Query();
        findByEmailQuery.addCriteria(Criteria.where("email").is(email));

        UserEntity userEntity = null;

        try {
            userEntity = mongoTemplate.findOne(findByEmailQuery, UserEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DataResponseException("유저 데이터를 불러오는 도중 오류.");
        }

        return Optional.ofNullable(userEntity);
    }

    @Override
    public String insert(UserEntity userEntity) {
        try {
            mongoTemplate.insert(userEntity);
        } catch (Exception e) {
            // 리펙토링 필요
            if(e.getClass().getSimpleName().equals("DuplicateKeyException")) {
                log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
                throw new DuplicateEmailException("이메일 중복 오류.");
            }

            throw e;
        }

        return userEntity.getEmail();
    }

    @Override
    public void deleteByEmail(String email) {
        mongoTemplate.remove(new Query(Criteria.where("email").is(email)), UserEntity.class);
    }

    public MongoDBUserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
