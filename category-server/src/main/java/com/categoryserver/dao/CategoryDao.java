package com.categoryserver.dao;

import com.categoryserver.domain.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    Optional<CategoryEntity> findById(String id);

    String insert(CategoryEntity categoryEntity);

    List<CategoryEntity> findAll();

    void update(CategoryEntity categoryEntity);

    String deleteById(String id);
}
