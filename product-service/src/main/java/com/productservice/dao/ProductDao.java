package com.productservice.dao;

import com.productservice.domain.ProductEntity;

import java.util.Optional;

public interface ProductDao {

    Optional<ProductEntity> findById(String id);

    String insert(ProductEntity productEntity);

    String deleteById(String id);
}
