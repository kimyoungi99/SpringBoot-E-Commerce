package com.ecommerce.service;

import com.ecommerce.servercommon.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    @Value(value = "${order.topic.name}")
    private String orderTopicName;

    public void sendOrderMessage(OrderDto orderDto) {
        log.info("주문 메세지 전송"); // 로그에 UID 추가
        orderDto.setOrderTime(LocalDateTime.now());
        this.kafkaTemplate.send(this.orderTopicName, orderDto);
    }
}
