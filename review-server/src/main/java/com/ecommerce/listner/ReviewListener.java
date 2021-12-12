package com.ecommerce.listner;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.review.Review;
import com.ecommerce.servercommon.domain.review.ReviewDao;
import com.ecommerce.servercommon.dto.OrderDto;
import com.ecommerce.servercommon.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewListener {

    private final ReviewDao reviewDao;

    // 주문 확정을 위한 리스너
    @KafkaListener(topics = "${review.topic.name}", containerFactory = "reviewConcurrentKafkaListenerContainerFactory")
    public void orderListener(ReviewDto reviewDto, Acknowledgment ack) {
        log.info("리뷰 리스닝 성공: " + reviewDto.toString());
        ack.acknowledge();

        Review review = reviewDto.toEntity();

        this.reviewDao.add(review);
        log.info("리뷰 저장 성공: " + reviewDto.toString());
    }
}
