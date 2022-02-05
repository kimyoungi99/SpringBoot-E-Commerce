package com.categoryserver.dao;

import com.categoryserver.domain.CategoryEntity;
import com.categoryserver.exception.DataResponseException;
import com.categoryserver.exception.DatabaseConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MongoDBCategoryDao implements CategoryId{

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<CategoryEntity> findById(String id) {
        Query findByIdQuery = new Query();
        findByIdQuery.addCriteria(Criteria.where("id").is(id));

        CategoryEntity categoryEntity = null;

        try {
            categoryEntity = mongoTemplate.findOne(findByIdQuery, CategoryEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DataResponseException("카테고리 데이터를 불러오는 도중 오류.");
        }

        return Optional.ofNullable(categoryEntity);
    }

    @Override
    public String insert(CategoryEntity categoryEntity) {
        try {
            mongoTemplate.insert(categoryEntity);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 연결 오류.");
        }

        return categoryEntity.getId();
    }

    @Override
    public List<CategoryEntity> findAll() {
        List<CategoryEntity> categoryEntities = null;

        try {
            categoryEntities = mongoTemplate.findAll(CategoryEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DataResponseException("카테고리 데이터를 불러오는 도중 오류.");
        }

        return categoryEntities;
    }

    @Override
    public void update(CategoryEntity categoryEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(categoryEntity.getId()));

        Update update = new Update();
        if (categoryEntity.getName() != null)
            update.set("name", categoryEntity.getName());

        try {
            mongoTemplate.updateFirst(query, update, CategoryEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 응답 오류.");
        }
    }

    @Override
    public String deleteById(String id) {
        try {
            mongoTemplate.remove(new Query(Criteria.where("id").is(id)), CategoryEntity.class);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new DatabaseConnectionException("데이터베이스 연결 오류.");
        }

        return id;
    }
}
