package com.userservice.dao;

import com.mongodb.MongoWriteException;
import com.userservice.domain.UserEntity;
import com.userservice.exception.DataResponseException;
import com.userservice.exception.DatabaseConnectionException;
import com.userservice.exception.DuplicateEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    public Optional<UserEntity> findById(String id) {
        Query findByIdQuery = new Query();
        findByIdQuery.addCriteria(Criteria.where("id").is(id));

        UserEntity userEntity = null;

        try {
            userEntity = mongoTemplate.findOne(findByIdQuery, UserEntity.class);
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
            if (e.getClass().getSimpleName().equals("DuplicateKeyException")) {
                log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
                throw new DuplicateEmailException("이메일 중복 오류.");
            }

            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }

        return userEntity.getId();
    }

    @Override
    public void update(UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userEntity.getId()));

        Update update = new Update();
        if (userEntity.getEmail() != null)
            update.set("email", userEntity.getEmail());
        if (userEntity.getAddress() != null)
            update.set("address", userEntity.getAddress());
        if (userEntity.getBirthdate() != null)
            update.set("birthdate", userEntity.getBirthdate());
        if (userEntity.getPassword() != null)
            update.set("password", userEntity.getPassword());
        if (userEntity.getPoint() != null)
            update.set("point", userEntity.getPoint());

        try {
            mongoTemplate.updateFirst(query, update, UserEntity.class);
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("DuplicateKeyException")) {
                log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
                throw new DuplicateEmailException("이메일 중복 오류.");
            }

            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
    }

    @Override
    public void deleteByEmail(String email) {
        try {
            mongoTemplate.remove(new Query(Criteria.where("email").is(email)), UserEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            mongoTemplate.remove(new Query(Criteria.where("id").is(id)), UserEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
    }

    public MongoDBUserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
