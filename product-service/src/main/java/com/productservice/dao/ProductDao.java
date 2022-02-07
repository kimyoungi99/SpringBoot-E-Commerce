package com.productservice.dao;

import com.productservice.domain.ProductEntity;

import java.util.Optional;

public interface ProductDao {

    Optional<ProductEntity> findById(String id);

    Optional<ProductEntity> findAndRemove(String id);

    String insert(ProductEntity productEntity);

    void update(ProductEntity productEntity);

    void updateSellerEmail(String sellerId, String sellerEmail);

    void updateCategoryName(String categoryId, String categoryName);

    String deleteById(String id);
}
