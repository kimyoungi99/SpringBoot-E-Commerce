package com.productservice.dao;

import com.productservice.domain.ProductEntity;
import com.productservice.exception.DataResponseException;
import com.productservice.exception.DatabaseConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

@Slf4j
public class MongoDBProductDao implements ProductDao {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<ProductEntity> findById(String id) {
        Query findByIdQuery = new Query();
        findByIdQuery.addCriteria(Criteria.where("id").is(id));

        ProductEntity productEntity = null;

        try {
            productEntity = mongoTemplate.findOne(findByIdQuery, ProductEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DataResponseException("유저 데이터를 불러오는 도중 오류.");
        }

        return Optional.ofNullable(productEntity);
    }

    @Override
    public String insert(ProductEntity productEntity) {
        try {
            mongoTemplate.insert(productEntity);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 연결 오류.");
        }

        return productEntity.getId();
    }

    @Override
    public String deleteById(String id) {
        try {
            mongoTemplate.remove(new Query(Criteria.where("id").is(id)), ProductEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 연결 오류.");
        }

        return id;
    }

    public MongoDBProductDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
