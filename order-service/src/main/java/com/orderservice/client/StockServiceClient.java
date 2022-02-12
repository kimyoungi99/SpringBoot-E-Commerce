package com.orderservice.client;

import com.orderservice.dto.StockCheckDto;
import com.orderservice.dto.StockUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "stock-service", url = "${feign.url.stock-service}")
public interface StockServiceClient {

    @PostMapping(value = "/stock-service/check", consumes = "application/json")
    Map<String, Object> check(@RequestParam("productId")String productId, @RequestParam("quantity")Long quantity);

    @PostMapping(value = "/stock-service/update", consumes = "application/json")
    Map<String, Object> update(@RequestParam("productId")String productId, @RequestParam("quantity")Long quantity);
}
