package com.productservice.dao;

import com.productservice.domain.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Optional<ProductEntity> findById(String id);

    Optional<ProductEntity> findAndRemove(String id);

    String insert(ProductEntity productEntity);

    List<ProductEntity> findAll();

    void update(ProductEntity productEntity);

    void updateSellerEmail(String sellerId, String sellerEmail);

    void updateCategoryName(String categoryId, String categoryName);

    void updateStock(String id, Long quantity);

    String deleteById(String id);
}
