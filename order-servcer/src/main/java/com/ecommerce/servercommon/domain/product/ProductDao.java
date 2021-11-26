package com.ecommerce.servercommon.domain.product;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDao {
    void add(Product product);

    Product findById(Long id);

    void deleteById(Long id);

    void update(Product product);

    List<Product> findAll();
}
