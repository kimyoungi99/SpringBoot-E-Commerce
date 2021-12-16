package com.ecommerce.servercommon.domain.product;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsDao {
    void add(ProductDetails productDetails);

    ProductDetails findById(Long id);

    void deleteById(Long id);

    void incrementSellCountByProductId(Long productId);

    void incrementReviewCountByProductId(Long productId);

    void update(ProductDetails productDetails);

    ProductDetails findByProductId(Long productId);

    ProductDetails findByOrderId(Long orderId);
}
