package com.ecommerce.service;

import com.ecommerce.servercommon.domain.product.ProductDetails;
import com.ecommerce.servercommon.domain.product.ProductDetailsDao;
import com.ecommerce.servercommon.domain.review.Review;
import com.ecommerce.servercommon.domain.review.ReviewDao;
import com.ecommerce.servercommon.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDao reviewDao;
    private final ProductDetailsDao productDetailsDao;

    public void addReview(ReviewDto reviewDto) {
        Review review = reviewDto.toEntity();

        this.reviewDao.add(review);
    }

    @Transactional
    public void updateRating(ReviewDto reviewDto) {
        ProductDetails pd = this.productDetailsDao.findByOrderId(reviewDto.getOrderId());
        BigDecimal sum = pd.getRating().multiply(new BigDecimal(pd.getReviewCount())).add(reviewDto.getStar());
        pd.setReviewCount(pd.getReviewCount() + 1);
        pd.setRating(sum.divide(new BigDecimal(pd.getReviewCount())).setScale(1));

        this.productDetailsDao.update(pd);
    }
}
