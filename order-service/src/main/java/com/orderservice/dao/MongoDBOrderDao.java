package com.orderservice.dao;

import com.orderservice.domain.OrderEntity;
import com.orderservice.exception.DataResponseException;
import com.orderservice.exception.DatabaseConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MongoDBOrderDao implements OrderDao {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<OrderEntity> findById(String id) {
        OrderEntity orderEntity = null;

        try {
            orderEntity = this.mongoTemplate.findById(id, OrderEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DataResponseException("유저 데이터를 불러오는 도중 오류.");
        }
        return Optional.ofNullable(orderEntity);
    }

    @Override
    public List<OrderEntity> findAllByBuyerId(String buyerId) {
        Query findAllByBuyerIdQuery = new Query();
        findAllByBuyerIdQuery.addCriteria(Criteria.where("buyerId").is(buyerId));

        List<OrderEntity> orderEntityList = null;

        try {
            orderEntityList = this.mongoTemplate.find(findAllByBuyerIdQuery, OrderEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DataResponseException("유저 데이터를 불러오는 도중 오류.");
        }

        return orderEntityList;
    }

    @Override
    public List<OrderEntity> findAllBySellerId(String sellerId) {
        Query findAllBySellerIdQuery = new Query();
        findAllBySellerIdQuery.addCriteria(Criteria.where("sellerId").is(sellerId));

        List<OrderEntity> orderEntityList = null;

        try {
            orderEntityList = this.mongoTemplate.find(findAllBySellerIdQuery, OrderEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DataResponseException("유저 데이터를 불러오는 도중 오류.");
        }

        return orderEntityList;
    }

    @Override
    public String insert(OrderEntity orderEntity) {
        try {
            this.mongoTemplate.insert(orderEntity);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }

        return orderEntity.getId();
    }

    @Override
    public void updateSellerEmail(String sellerId, String sellerEmail) {
        Query updateSellerEmailQuery = new Query();
        updateSellerEmailQuery.addCriteria(Criteria.where("sellerId").is(sellerId));

        Update update = new Update();
        if (sellerEmail != null)
            update.set("sellerEmail", sellerEmail);

        try {
            this.mongoTemplate.updateMulti(updateSellerEmailQuery, update, OrderEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            mongoTemplate.remove(new Query(Criteria.where("id").is(id)), OrderEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
    }

    public MongoDBOrderDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
