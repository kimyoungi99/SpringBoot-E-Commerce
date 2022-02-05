package com.categoryserver.dao;

import com.categoryserver.common.config.MongoDBConfig;
import com.categoryserver.domain.CategoryEntity;
import com.categoryserver.exception.DataResponseException;
import com.categoryserver.exception.DatabaseConnectionException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@Import(MongoDBConfig.class)
@TestPropertySource(locations = "/application.properties")
class MongoDBCategoryDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    MongoDBCategoryDao categoryDao;

    MongoDBCategoryDao wrongCategoryDao;

    CategoryEntity categoryEntity1;

    @BeforeEach
    public void init() {
        this.categoryDao = new MongoDBCategoryDao(this.mongoTemplate);
        this.categoryEntity1 = CategoryEntity.builder()
                .name("electronics")
                .build();
        this.wrongCategoryDao = new MongoDBCategoryDao(
                new MongoTemplate(
                        new SimpleMongoClientDatabaseFactory("mongodb://localhost:9909/product")
                )
        );
    }

    @AfterEach
    public void after() {
        this.categoryDao.deleteById(this.categoryEntity1.getId());
    }

    @Test
    @DisplayName("저장 & id로 조회 테스트")
    public void insertAndFindByIdTest() {
        this.categoryDao.insert(this.categoryEntity1);
        CategoryEntity categoryEntity = this.categoryDao.findById(this.categoryEntity1.getId()).get();

        checkSameCategoryEntity(this.categoryEntity1, categoryEntity);
    }

    @Test
    @DisplayName("삭제 테스트")
    public void deleteTest() {
        this.categoryDao.insert(this.categoryEntity1);
        this.categoryDao.deleteById(this.categoryEntity1.getId());

        assertThat(this.categoryDao.findById(this.categoryEntity1.getId())).isEmpty();
    }

    @Test
    @DisplayName("싱품 수정 테스트")
    public void updateTest() {
        this.categoryDao.insert(this.categoryEntity1);
        CategoryEntity update = CategoryEntity.builder()
                .id(this.categoryEntity1.getId())
                .name("hello")
                .build();

        this.categoryDao.update(update);
        CategoryEntity categoryEntity = this.categoryDao.findById(update.getId()).get();

        checkSameCategoryEntity(categoryEntity, update);
    }

    @Test
    @Disabled
    @DisplayName("데이터 베이스 연결 오류 테스트")
    public void databaseConnectionExceptionTest() {
        assertThrows(DatabaseConnectionException.class, () -> {
            this.wrongCategoryDao.deleteById("asdf");
        });
    }

    @Test
    @Disabled
    @DisplayName("데이터 응답 오류 테스트")
    public void dataResponseExceptionTest() {
        assertThrows(DataResponseException.class, () -> {
            this.wrongCategoryDao.findById("asdf");
        });
    }

    private void checkSameCategoryEntity(CategoryEntity categoryEntity1, CategoryEntity categoryEntity2) {
        assertThat(categoryEntity1.getId()).isEqualTo(categoryEntity1.getId());
        assertThat(categoryEntity1.getName()).isEqualTo(categoryEntity2.getName());
    }
}