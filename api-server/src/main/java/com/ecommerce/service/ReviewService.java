package com.ecommerce.service;

import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final KafkaTemplate<String, ReviewDto> kafkaTemplate;
    private final UserDao userDao;

    @Value(value = "${review.topic.name}")
    private String reviewTopicName;

    public void sendReviewMessage(ReviewDto reviewDto, String userEmail) {
        reviewDto.setReviewerId(this.userDao.findByEmail(userEmail).getId());
        this.kafkaTemplate.send(this.reviewTopicName, reviewDto);
        log.info("리뷰 메세지 전송: " + reviewDto.toString());
    }
}
