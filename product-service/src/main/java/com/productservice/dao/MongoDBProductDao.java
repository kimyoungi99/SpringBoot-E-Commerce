package com.productservice.dao;

import com.productservice.domain.ProductEntity;
import com.productservice.exception.DataResponseException;
import com.productservice.exception.DatabaseConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
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
    public void update(ProductEntity productEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(productEntity.getId()));

        Update update = new Update();
        if (productEntity.getSellerId() != null)
            update.set("sellerId", productEntity.getSellerId());
        if (productEntity.getSellerEmail() != null)
            update.set("sellerEmail", productEntity.getSellerEmail());
        if (productEntity.getName() != null)
            update.set("name", productEntity.getName());
        if (productEntity.getStock() != null)
            update.set("stock", productEntity.getStock());
        if (productEntity.getTotalSales() != null)
            update.set("totalSales", productEntity.getTotalSales());
        if (productEntity.getPrice() != null)
            update.set("price", productEntity.getPrice());
        if (productEntity.getCategoryId() != null)
            update.set("categoryId", productEntity.getCategoryId());

        try {
            mongoTemplate.updateFirst(query, update, ProductEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
    }

    @Override
    public void updateSellerEmail(String sellerId, String sellerEmail) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sellerId").is(sellerId));

        Update update = new Update();
        update.set("sellerEmail", sellerEmail);

        try {
            mongoTemplate.updateMulti(query, update, ProductEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
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
