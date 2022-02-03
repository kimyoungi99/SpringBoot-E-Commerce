package com.productservice.dao;

import com.productservice.domain.ProductEntity;

import java.util.Optional;

public interface ProductDao {

    Optional<ProductEntity> findById(String id);

    String insert(ProductEntity productEntity);

    void update(ProductEntity productEntity);

    void updateSellerEmail(String sellerId, String sellerEmail);

    String deleteById(String id);
}
