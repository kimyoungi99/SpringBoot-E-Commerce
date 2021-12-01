package com.ecommerce.restcontroller;

import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.servercommon.dto.ProductDto;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/add")
    public HttpResponseDto addProduct(@RequestBody ProductDto productDto, Authentication authentication) {

        if(authentication == null || authentication.getName() == "anonymousUser")
            throw new IllegalArgumentException();

        this.productService.addProduct(productDto, authentication.getName());
        return new HttpResponseDto(
                HttpStatus.OK,
                "상품 등록 성공",
                null
        );
    }
}
