package com.ecommerce.listner;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.order.Order;
import com.ecommerce.servercommon.domain.order.OrderDao;
import com.ecommerce.servercommon.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderDao orderDao;

    // 주문 확정을 위한 리스너
    @KafkaListener(topics = "${order.topic.name}", containerFactory = "orderConcurrentKafkaListenerContainerFactory")
    public void orderListener(OrderDto orderDto, Acknowledgment ack) {
        log.info("주문 리스닝 성공: " + orderDto.toString());
        ack.acknowledge();

        Order order = orderDto.toEntity();
        order.setOrderStatus(OrderStatus.PAYED);

        orderDao.add(order);
        log.info("주문 저장 성공: " + orderDto.toString());
    }
}
