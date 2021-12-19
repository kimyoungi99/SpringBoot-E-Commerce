package com.ecommerce.servercommon.domain.product;

import com.ecommerce.servercommon.dto.ProductWithDetailsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

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

    void updateSellCountAndStockByProductId(HashMap<String, Long> param);

    ProductWithDetailsDto findProductWithDetailsByProductId(Long productId);
}
