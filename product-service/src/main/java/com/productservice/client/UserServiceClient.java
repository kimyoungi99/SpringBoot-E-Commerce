package com.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(value = "user-service", url = "${feign.url.user-service}")
public interface UserServiceClient {

    @GetMapping("/user-service/getEmail/{sellerId}")
    Map<String, Object> getEmail(@PathVariable String sellerId);
}
