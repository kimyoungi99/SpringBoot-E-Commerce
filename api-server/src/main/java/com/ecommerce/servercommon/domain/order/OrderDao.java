package com.ecommerce.servercommon.domain.order;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    void add(Order order);

    Order findById(Long id);

    void deleteById(Long id);

    void update(Order order);

    List<Order> findAll();
}
