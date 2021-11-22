package com.ecommerce.common.domain.product;

import com.ecommerce.common.domain.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {
    public void add(Product product);

    public Product findById(Long id);

    public void deleteById(Long id);

    public void update(Product product);
}
