package com.ecommerce.restcontroller;

import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 임시 (추후 Search 구현)
    @GetMapping(value = "/getall")
    public HttpResponseDto getAllProduct() {
        return new HttpResponseDto(
                HttpStatus.OK,
                "상품 불러오기 성공",
                this.productService.getAllProduct()
        );
    }
}
