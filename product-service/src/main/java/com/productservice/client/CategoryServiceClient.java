package com.productservice.client;

import com.productservice.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(value = "category-service", url = "${feign.url.category-service}")
public interface CategoryServiceClient {

    @GetMapping("/category-service/info/{categoryId}")
    Map<String, Object> info(@PathVariable String categoryId);
}
