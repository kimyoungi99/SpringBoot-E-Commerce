package com.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(value = "product-service", url = "${feign.url.product-service}")
public interface ProductServiceClient {

    @GetMapping("/product-service/infoForOrder/{productId}")
    Map<String, Object> getProduct(@PathVariable String productId);
}
