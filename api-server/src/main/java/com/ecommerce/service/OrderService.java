package com.ecommerce.service;

import com.ecommerce.common.exception.*;
import com.ecommerce.servercommon.domain.order.Order;
import com.ecommerce.servercommon.domain.order.OrderDao;
import com.ecommerce.servercommon.domain.product.ProductDetailsDao;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.OrderDto;
import com.ecommerce.servercommon.dto.OrderResponseDto;
import com.ecommerce.servercommon.dto.ProductWithDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ProductDetailsDao productDetailsDao;

    @Value(value = "${order.topic.name}")
    private String orderTopicName;

    public void sendOrderMessage(OrderDto orderDto, String userEmail) {
        User buyer = this.userDao.findByEmail(userEmail);

        validateOrder(orderDto, buyer);

        orderDto.setOrderTime(LocalDateTime.now());
        orderDto.setBuyerId(buyer.getId());
        this.kafkaTemplate.send(this.orderTopicName, orderDto);
        log.info("주문 메세지 전송: " + orderDto.toString());
    }

    public List<OrderResponseDto> getAllOrder(String userEmail) {
        List<Order> orderList = orderDao.findAllByUserEmail(userEmail);
        return orderList.stream().map(Order::toResponseDto).collect(Collectors.toList());
    }

    public void validateOrder(OrderDto orderDto, User buyer) {
        ProductWithDetailsDto productWithDetailsDto = this.productDetailsDao.findProductWithDetailsByProductId(orderDto.getProductId());
        OrderException e = null;
        if (productWithDetailsDto == null) {
            e = new NotExistingProductException("상품 미존재 오류");
        }
        else if (productWithDetailsDto.getStock() - orderDto.getQuantity() < 1) {
            e = new SoldOutProductException("품절 상품 오류");
        }
        else if (orderDto.getPay() + orderDto.getUsePoint() != productWithDetailsDto.getPrice()) {
            e = new PaymentException("가격 오류");
        }
        else if (buyer.getPoint() < orderDto.getUsePoint()) {
            e = new PointException("포인트 부족 오류");
        }
        else {
            return;
        }

        log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
        throw e;
    }
}
