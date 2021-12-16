package com.ecommerce.service;

import com.ecommerce.servercommon.domain.product.ProductDetailsDao;
import com.ecommerce.servercommon.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCountService {

    private final ProductDetailsDao productDetailsDao;

    public void updateCount(OrderDto orderDto) {
        this.productDetailsDao.incrementSellCountByProductId(orderDto.getProductId());
    }
}
