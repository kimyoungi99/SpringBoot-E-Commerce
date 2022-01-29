package com.userservice.dao;

import com.userservice.common.config.MongoDBConfig;
import com.userservice.domain.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(MongoDBConfig.class)
class MongoDBUserDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    MongoDBUserDao userDao;

    // test data
    UserEntity userEntity1;

    @BeforeEach
    public void init() {
        this.userDao = new MongoDBUserDao(mongoTemplate);
        this.userEntity1 = UserEntity.builder()
                .email("kimyoungi99@naver9.com")
                .password("Asdf")
                .address("seoul, korea")
                .birthdate("1999/03/18")
                .point(0L)
                .createdDate(new Date())
                .build();
    }

    @AfterEach
    public void after() {
        this.userDao.deleteByEmail(this.userEntity1.getEmail());
    }

    @Test
    @DisplayName("MongoDB 유저 저장 & 이메일로 조회 테스트")
    public void insertAndFindByEmailTest() {

        this.userDao.insert(this.userEntity1);
        UserEntity userEntity = this.userDao.findByEmail(this.userEntity1.getEmail()).get();

        this.checkSameUserEntity(this.userEntity1, userEntity);
    }

    @Test
    @DisplayName("MongoDB 유저 삭제 테스트")
    public void deleteTest() {

        this.userDao.insert(this.userEntity1);
        this.userDao.deleteByEmail(this.userEntity1.getEmail());

        assertThat(this.userDao.findByEmail(this.userEntity1.getEmail())).isEmpty();
    }

    private void checkSameUserEntity(UserEntity userEntity1, UserEntity userEntity2) {
        assertThat(userEntity1.getEmail()).isEqualTo(userEntity2.getEmail());
        assertThat(userEntity1.getPassword()).isEqualTo(userEntity2.getPassword());
        assertThat(userEntity1.getAddress()).isEqualTo(userEntity2.getAddress());
        assertThat(userEntity1.getBirthdate()).isEqualTo(userEntity2.getBirthdate());
        assertThat(userEntity1.getPoint()).isEqualTo(userEntity2.getPoint());
        assertThat(userEntity1.getCreatedDate()).isEqualTo(userEntity2.getCreatedDate());
    }
}