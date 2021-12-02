package com.ecommerce.restcontroller;

import com.ecommerce.common.config.security.AuthenticationValidator;
import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.common.response.ResponseBuilder;
import com.ecommerce.servercommon.dto.ProductDto;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AuthenticationValidator authenticationValidator;
    private final ResponseBuilder responseBuilder;

    // 임시 (추후 Search 구현)
    @GetMapping(value = "/getall")
    public ResponseEntity<HttpResponseDto> getAllProduct() {
        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "상품 불러오기 성공",
                this.productService.getAllProduct()
        );
    }

    @PostMapping(value = "/add")
    public ResponseEntity<HttpResponseDto> addProduct(
            @RequestBody ProductDto productDto,
            Authentication authentication
    ) throws AuthenticationException {

        this.productService.addProduct(
                productDto,
                authenticationValidator.validateAndGetName(authentication)
        );

        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "상품 등록 성공",
                null
        );
    }
}
