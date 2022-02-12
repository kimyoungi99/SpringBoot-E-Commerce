package com.orderservice.dao;

import com.orderservice.domain.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<OrderEntity> findById(String id);

    List<OrderEntity> findAllByBuyerId(String buyerId);

    List<OrderEntity> findAllBySellerId(String sellerId);

    String insert(OrderEntity orderEntity);

    void updateSellerEmail(String sellerId, String sellerEmail);

    void deleteById(String id);
}
