package com.apigateway.client;

import com.apigateway.dto.OrderAddDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "order-service", url = "${feign.url.order-service}")
public interface OrderServiceClient {

    @PostMapping("/order-service/order")
    Map<String, Object> order(OrderAddDto orderAddDto);

    @GetMapping(value = "/order-service/getOrderList/{buyerId}")
    Map<String, Object> getOrderList(@PathVariable String buyerId);

    @GetMapping(value = "/order-service/getSellList/{sellerId}")
    Map<String, Object> getSellList(@PathVariable String sellerId);
}
