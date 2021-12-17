package com.ecommerce.service;

import com.ecommerce.servercommon.domain.product.ProductDetailsDao;
import com.ecommerce.servercommon.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OrderCountService {

    private final ProductDetailsDao productDetailsDao;

    public void updateCount(OrderDto orderDto) {
        HashMap<String, Long> param = new HashMap<>();
        param.put("productId", orderDto.getProductId());
        param.put("quantity", Long.valueOf(orderDto.getQuantity()));
        this.productDetailsDao.updateSellCountAndStockByProductId(param);
    }
}
