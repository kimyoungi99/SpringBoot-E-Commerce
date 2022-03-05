package com.apigateway.client;

import com.apigateway.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "user-service", url = "${feign.url.user-service}")
public interface UserServiceClient {

    @PostMapping("/user-service/validate")
    Map<String, Object> validate(TokenDto tokenDto);

    @PostMapping("/user-service/join")
    Map<String, Object> join(UserJoinDto userJoinDto);

    @PostMapping("/user-service/login")
    Map<String, Object> login(UserLoginDto userLoginDto);

    @PostMapping("/user-service/update")
    Map<String, Object> update(UserUpdateDto userUpdateDto);
}
