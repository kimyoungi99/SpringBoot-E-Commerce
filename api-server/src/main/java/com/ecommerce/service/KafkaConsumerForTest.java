package com.ecommerce.service;

import com.ecommerce.servercommon.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerForTest {

    @KafkaListener(topics = "${order.topic.name}", containerFactory = "orderConcurrentKafkaListenerContainerFactory")
    public void orderListener(OrderDto orderDto, Acknowledgment ack) {
        log.info("리스닝 성공: " + orderDto.toString());
        ack.acknowledge();
    }
}
