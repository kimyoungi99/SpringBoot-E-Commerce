package com.userservice.dao;

import com.userservice.domain.UserEntity;
import com.userservice.exception.DataResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;


@Slf4j
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
        mongoTemplate.insert(userEntity);
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
