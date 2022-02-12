package com.orderservice.dao;

import com.orderservice.common.config.MongoDBConfig;
import com.orderservice.domain.OrderEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(MongoDBConfig.class)
@TestPropertySource(locations = "/application.properties")
class MongoDBOrderDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    OrderDao orderDao;

    // test data
    OrderEntity orderEntity1;
    OrderEntity orderEntity2;

    @BeforeEach
    public void init() {
        this.orderDao = new MongoDBOrderDao(this.mongoTemplate);
        this.orderEntity1 = OrderEntity.builder()
                .productId("asdf")
                .productName("infinite")
                .quantity(10L)
                .sellerId("asdfasdf")
                .sellerEmail("challenge")
                .buyerId("12341234")
                .moneyPayed(10000L)
                .pointPayed(5000L)
                .orderTime(LocalDateTime.now())
                .address("asdfasdfasdfsadfsadfsa")
                .build();
        this.orderEntity2 = OrderEntity.builder()
                .productId("asdf")
                .productName("infinite challenge")
                .quantity(1L)
                .sellerId("asdfasdf")
                .sellerEmail("challenge")
                .buyerId("12341234")
                .moneyPayed(1000000L)
                .pointPayed(50000L)
                .orderTime(LocalDateTime.now())
                .address("asdfasdfasdfsadfsadfsa")
                .build();
    }

    @AfterEach
    public void after() {
        this.orderDao.deleteById(this.orderEntity1.getId());
        this.orderDao.deleteById(this.orderEntity2.getId());
    }

    @Test
    @DisplayName("주문 저장 & id로 조회 테스트")
    public void insertAndFindByIdTest() {
        this.orderDao.insert(this.orderEntity1);
        OrderEntity orderEntity = this.orderDao.findById(this.orderEntity1.getId()).get();

        checkSameOrderEntity(orderEntity, this.orderEntity1);
    }

    @Test
    @DisplayName("구매자 아이디로 조회 테스트")
    public void findAllByBuyerIdTest() {
        this.orderDao.insert(this.orderEntity1);
        this.orderDao.insert(this.orderEntity2);
        List<OrderEntity> orderEntityList = this.orderDao.findAllByBuyerId(this.orderEntity1.getBuyerId());

        assertThat(orderEntityList.size()).isEqualTo(2);
        checkSameOrderEntity(orderEntityList.get(0), this.orderEntity1);
        checkSameOrderEntity(orderEntityList.get(1), this.orderEntity2);
    }

    @Test
    @DisplayName("판매자 아이디로 조회 테스트")
    public void findAllBySellerId() {
        this.orderDao.insert(this.orderEntity1);
        this.orderDao.insert(this.orderEntity2);
        List<OrderEntity> orderEntityList = this.orderDao.findAllBySellerId(this.orderEntity1.getSellerId());

        checkSameOrderEntity(orderEntityList.get(0), this.orderEntity1);
        checkSameOrderEntity(orderEntityList.get(1), this.orderEntity2);
    }

    @Test
    @DisplayName("판매자 이메일 수정 테스트")
    public void updateSellerEmailTest() {
        String newEmail = "hello";
        this.orderDao.insert(this.orderEntity1);
        this.orderDao.updateSellerEmail(this.orderEntity1.getSellerId(), newEmail);

        assertThat(this.orderDao.findById(this.orderEntity1.getId()).get().getSellerEmail()).isEqualTo(newEmail);
    }

    @Test
    @DisplayName("id로 삭제 테스트")
    public void deleteByIdTest() {
        this.orderDao.insert(this.orderEntity1);
        this.orderDao.deleteById(this.orderEntity1.getId());

        assertThat(this.orderDao.findById(this.orderEntity1.getId())).isEmpty();
    }

    private void checkSameOrderEntity(OrderEntity orderEntity1, OrderEntity orderEntity2) {
        assertThat(orderEntity1.getId()).isEqualTo(orderEntity2.getId());
        assertThat(orderEntity1.getProductId()).isEqualTo(orderEntity2.getProductId());
        assertThat(orderEntity1.getProductName()).isEqualTo(orderEntity2.getProductName());
        assertThat(orderEntity1.getQuantity()).isEqualTo(orderEntity2.getQuantity());
        assertThat(orderEntity1.getSellerId()).isEqualTo(orderEntity2.getSellerId());
        assertThat(orderEntity1.getSellerEmail()).isEqualTo(orderEntity2.getSellerEmail());
        assertThat(orderEntity1.getBuyerId()).isEqualTo(orderEntity2.getBuyerId());
        assertThat(orderEntity1.getMoneyPayed()).isEqualTo(orderEntity2.getMoneyPayed());
        assertThat(orderEntity1.getPointPayed()).isEqualTo(orderEntity2.getPointPayed());

    }
}