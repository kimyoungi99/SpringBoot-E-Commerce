package com.apigateway.client;

import com.apigateway.dto.CategoryAddDto;
import com.apigateway.dto.CategoryUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "category-service", url = "${feign.url.category-service}")
public interface CategoryServiceClient {

    @GetMapping(value = "/category-service/all")
    Map<String, Object> getAll();

    @PostMapping(value = "/category-service/update")
    Map<String, Object> update(CategoryUpdateDto categoryUpdateDto);

    @PostMapping(value = "/category-service/add")
    Map<String, Object> add(CategoryAddDto categoryAddDto);
}
