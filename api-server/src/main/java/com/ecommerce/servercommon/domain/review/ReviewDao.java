package com.ecommerce.servercommon.domain.review;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewDao {
    void add(Review review);

    Review findById(Long id);

    List<Review> findAllByReviewerId(Long reviewerId);

    List<Review> findAllByProductId(Long productId);

    void deleteById(Long id);
}
