package com.ecommerce.servercommon.domain.order;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    void add(Order order);

    List<Order> findAll();

    Order findById(Long id);

    void update(Order order);

    void deleteById(Long id);

    List<Order> findAllByUserEmail(String userEmail);
}
