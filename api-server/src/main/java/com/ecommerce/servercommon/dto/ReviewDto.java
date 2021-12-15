package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.review.Review;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ReviewDto implements Serializable {
    private Long orderId;
    private Long reviewerId;
    private BigDecimal star;
    private String review;

    public Review toEntity() {
        return Review.builder()
                .orderId(this.orderId)
                .reviewerId(this.reviewerId)
                .star(this.star)
                .review(this.review)
                .build();
    }
}
