package com.ecommerce.service;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.order.Order;
import com.ecommerce.servercommon.domain.order.OrderDao;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;

    @Transactional
    public void orderConfirm(OrderDto orderDto) {
        User buyer = this.userDao.findById(orderDto.getBuyerId());
        // 리펙토링 필요
        if(buyer.getPoint() < orderDto.getUsePoint())
            throw new IllegalStateException("포인트 오류");
        buyer.setPoint(buyer.getPoint() - orderDto.getUsePoint());

        Order order = orderDto.toEntityWithOrderStatus(OrderStatus.PAYED);
        order.setOrderStatus(OrderStatus.PAYED);

        orderDao.add(order);
        userDao.update(buyer);
    }

}
