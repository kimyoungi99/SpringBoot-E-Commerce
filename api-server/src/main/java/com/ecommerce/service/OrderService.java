package com.ecommerce.service;

import com.ecommerce.servercommon.domain.order.Order;
import com.ecommerce.servercommon.domain.order.OrderDao;
import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.OrderDto;
import com.ecommerce.servercommon.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaTemplate<String, OrderDto> kafkaTemplate;
    private final UserDao userDao;
    private final OrderDao orderDao;

    @Value(value = "${order.topic.name}")
    private String orderTopicName;

    public void sendOrderMessage(OrderDto orderDto, String userEmail) {
        orderDto.setOrderTime(LocalDateTime.now());
        orderDto.setBuyerId(this.userDao.findByEmail(userEmail).getId());
        this.kafkaTemplate.send(this.orderTopicName, orderDto);
        log.info("주문 메세지 전송: " + orderDto.toString());
    }

    public List<OrderResponseDto> getAllOrder(String userEmail) {
        List<Order> orderList = orderDao.findAllByUserEmail(userEmail);
        return orderList.stream().map(Order::toResponseDto).collect(Collectors.toList());
    }
}
