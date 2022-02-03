package com.userservice.dao;

import com.userservice.common.config.MongoDBConfig;
import com.userservice.domain.UserEntity;
import com.userservice.exception.DataResponseException;
import com.userservice.exception.DatabaseConnectionException;
import com.userservice.exception.DuplicateEmailException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@Import(MongoDBConfig.class)
@TestPropertySource(locations = "/application.properties")
class MongoDBUserDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    MongoDBUserDao userDao;

    MongoDBUserDao wrongUserDao;

    // test data
    UserEntity userEntity1;

    @BeforeEach
    public void init() {
        this.userDao = new MongoDBUserDao(mongoTemplate);
        this.userEntity1 = UserEntity.builder()
                .email("kimyoungi99@naver9.com")
                .password("Asdf")
                .address("seoul, korea")
                .birthdate(LocalDate.parse(
                        "1999-03-18",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ))
                .point(0L)
                .createdDate(new Date())
                .build();
        this.wrongUserDao = new MongoDBUserDao(
                new MongoTemplate(
                        new SimpleMongoClientDatabaseFactory("mongodb://192.168.12.12:9903/product")
                )
        );
    }

    @AfterEach
    public void after() {
        this.userDao.deleteById(this.userEntity1.getId());
    }

    @Test
    @DisplayName("유저 저장 & 이메일로 조회 테스트")
    public void insertAndFindByEmailTest() {

        this.userDao.insert(this.userEntity1);
        UserEntity userEntity = this.userDao.findByEmail(this.userEntity1.getEmail()).get();

        checkSameUserEntity(this.userEntity1, userEntity);
    }

    @Test
    @DisplayName("유저 저장 & id로 조회 테스트")
    public void insertAndFindByIdTest() {

        this.userDao.insert(this.userEntity1);
        UserEntity userEntity = this.userDao.findById(this.userEntity1.getId()).get();

        checkSameUserEntity(this.userEntity1, userEntity);
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    public void deleteTest() {

        this.userDao.insert(this.userEntity1);
        this.userDao.deleteByEmail(this.userEntity1.getEmail());

        assertThat(this.userDao.findByEmail(this.userEntity1.getEmail())).isEmpty();
    }

    @Test
    @DisplayName("유저 수정 테스트")
    public void updateTest() {
        this.userDao.insert(this.userEntity1);
        UserEntity update = UserEntity.builder()
                .id(this.userEntity1.getId())
                .email("yk0318ha@naver9.com")
                .password("Asdfasdf")
                .address("busan, korea")
                .birthdate(LocalDate.parse(
                        "1969-05-18",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ))
                .point(123L)
                .createdDate(this.userEntity1.getCreatedDate())
                .build();

        this.userDao.update(update);
        UserEntity userEntity = this.userDao.findById(this.userEntity1.getId()).get();

        checkSameUserEntity(userEntity, update);
    }

    @Test
    @DisplayName("중복 이메일 가입 테스트")
    public void duplicateEmailException() {
        this.userDao.insert(this.userEntity1);

        assertThrows(DuplicateEmailException.class, () -> {
            this.userDao.insert(UserEntity.builder()
                    .email("kimyoungi99@naver9.com")
                    .build());
        });
    }

    @Test
    @Disabled
    @DisplayName("데이터 베이스 연결 오류 테스트")
    public void databaseConnectionExceptionTest() {
        assertThrows(DatabaseConnectionException.class, () -> {
            this.wrongUserDao.deleteByEmail("asdf");
        });
    }

    @Test
    @Disabled
    @DisplayName("데이터 응답 오류 테스트")
    public void dataResponseExceptionTest() {
        assertThrows(DataResponseException.class, () -> {
            this.wrongUserDao.findByEmail("asdf");
        });
    }

    private void checkSameUserEntity(UserEntity userEntity1, UserEntity userEntity2) {
        assertThat(userEntity1.getId()).isEqualTo(userEntity2.getId());
        assertThat(userEntity1.getEmail()).isEqualTo(userEntity2.getEmail());
        assertThat(userEntity1.getPassword()).isEqualTo(userEntity2.getPassword());
        assertThat(userEntity1.getAddress()).isEqualTo(userEntity2.getAddress());
        assertThat(userEntity1.getBirthdate()).isEqualTo(userEntity2.getBirthdate());
        assertThat(userEntity1.getPoint()).isEqualTo(userEntity2.getPoint());
        assertThat(userEntity1.getCreatedDate()).isEqualTo(userEntity2.getCreatedDate());
    }
}