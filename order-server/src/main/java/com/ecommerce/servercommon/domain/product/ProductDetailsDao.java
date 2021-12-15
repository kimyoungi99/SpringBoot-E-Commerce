package com.ecommerce.servercommon.domain.product;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsDao {
    void add(ProductDetails productDetails);

    ProductDetails findById(Long id);

    void deleteById(Long id);

    void incrementSellCount(Long id);

    void incrementReviewCount(Long id);
}
