package com.apigateway.client;

import com.apigateway.dto.OrderAddDto;
import com.apigateway.dto.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "user-service", url = "${feign.url.user-service}")
public interface UserServiceClient {

    @PostMapping("/user-service/validate")
    Map<String, Object> validate(TokenDto tokenDto);
}
