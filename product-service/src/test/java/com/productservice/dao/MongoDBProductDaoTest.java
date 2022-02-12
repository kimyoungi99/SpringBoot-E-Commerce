package com.productservice.dao;

import com.productservice.common.config.MongoDBConfig;
import com.productservice.domain.ProductEntity;
import com.productservice.exception.DataResponseException;
import com.productservice.exception.DatabaseConnectionException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo .DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataMongoTest
@Import(MongoDBConfig.class)
@TestPropertySource(locations = "/application.properties")
class MongoDBProductDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    MongoDBProductDao productDao;

    MongoDBProductDao wrongProductDao;

    // test data
    ProductEntity productEntity1;

    @BeforeEach
    public void init() {
        this.productDao = new MongoDBProductDao(this.mongoTemplate);
        this.productEntity1 = ProductEntity.builder()
                .name("nike sup")
                .categoryId("1234134")
                .categoryName("shoes")
                .price(1000000L)
                .stock(1L)
                .totalSales(100L)
                .createdDate(new Date())
                .build();
        this.wrongProductDao = new MongoDBProductDao(
                new MongoTemplate(
                        new SimpleMongoClientDatabaseFactory("mongodb://192.168.12.12:9903/product")
                )
        );
    }

    @AfterEach
    public void after() {
        this.productDao.deleteById(this.productEntity1.getId());
    }

    @Test
    @DisplayName("상품 저장 & id로 조회 테스트")
    public void insertAndFindByIdTest() {
        this.productDao.insert(this.productEntity1);
        ProductEntity productEntity = this.productDao.findById(this.productEntity1.getId()).get();

        checkSameProductEntity(this.productEntity1, productEntity);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void deleteTest() {
        this.productDao.insert(this.productEntity1);
        this.productDao.deleteById(this.productEntity1.getId());

        assertThat(this.productDao.findById(this.productEntity1.getId())).isEmpty();
    }

    @Test
    @DisplayName("싱품 수정 테스트")
    public void updateTest() {
        this.productDao.insert(this.productEntity1);
        ProductEntity update = ProductEntity.builder()
                .id(this.productEntity1.getId())
                .sellerEmail("sadf.asdf")
                .sellerId("123123")
                .stock(100L)
                .totalSales(100L)
                .price(1231230L)
                .name("hello")
                .categoryId("Asdfasdf")
                .categoryName("aaaa")
                .createdDate(this.productEntity1.getCreatedDate())
                .build();

        this.productDao.update(update);
        ProductEntity productEntity = this.productDao.findById(update.getId()).get();

        checkSameProductEntity(productEntity, update);
    }

    @Test
    @DisplayName("판매자 이메일 수정 테스트")
    public void updateSellerEmailTest() {
        this.productDao.insert(this.productEntity1);
        String newEmail = "SAfd.asdf";

        this.productDao.updateSellerEmail(this.productEntity1.getSellerId(), newEmail);

        this.productEntity1.setSellerEmail(newEmail);
        ProductEntity productEntity = this.productDao.findById(this.productEntity1.getId()).get();

        checkSameProductEntity(this.productEntity1, productEntity);
    }

    @Test
    @DisplayName("카테고리 이름 수정 테스트")
    public void updateCategoryNameTest() {
        this.productDao.insert(this.productEntity1);
        String newCategory = "asdfasdfasdf";

        this.productDao.updateCategoryName(this.productEntity1.getCategoryId(), newCategory);

        this.productEntity1.setCategoryName(newCategory);
        ProductEntity productEntity = this.productDao.findById(this.productEntity1.getId()).get();

        checkSameProductEntity(this.productEntity1, productEntity);
    }

    @Test
    @DisplayName("findAndRemove 테스트")
    public void findAndRemoveTest() {
        this.productDao.insert(this.productEntity1);
        ProductEntity productEntity = this.productDao.findAndRemove(this.productEntity1.getId()).get();

        assertThat(this.productDao.findById(this.productEntity1.getId())).isEmpty();
        checkSameProductEntity(productEntity, this.productEntity1);
    }

    @Test
    @Disabled
    @DisplayName("데이터 베이스 연결 오류 테스트")
    public void databaseConnectionExceptionTest() {
        assertThrows(DatabaseConnectionException.class, () -> {
            this.wrongProductDao.deleteById("asdf");
        });
    }

    @Test
    @Disabled
    @DisplayName("데이터 응답 오류 테스트")
    public void dataResponseExceptionTest() {
        assertThrows(DataResponseException.class, () -> {
            this.wrongProductDao.findById("asdf");
        });
    }

    private void checkSameProductEntity(ProductEntity productEntity1, ProductEntity productEntity2) {
        assertThat(productEntity1.getId()).isEqualTo(productEntity2.getId());
        assertThat(productEntity1.getCategoryId()).isEqualTo(productEntity2.getCategoryId());
        assertThat(productEntity1.getPrice()).isEqualTo(productEntity2.getPrice());
        assertThat(productEntity1.getStock()).isEqualTo(productEntity2.getStock());
        assertThat(productEntity1.getTotalSales()).isEqualTo(productEntity2.getTotalSales());
        assertThat(productEntity1.getName()).isEqualTo(productEntity2.getName());
        assertThat(productEntity1.getCreatedDate()).isEqualTo(productEntity2.getCreatedDate());
    }
}