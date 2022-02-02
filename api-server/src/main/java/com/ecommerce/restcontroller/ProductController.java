package com.ecommerce.restcontroller;

import com.ecommerce.common.security.AuthenticationValidator;
import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.common.response.ResponseBuilder;
import com.ecommerce.servercommon.dto.ProductAddDto;
import com.ecommerce.servercommon.dto.ProductUpdateDto;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<HttpResponseDto> addProduct(
            @RequestBody ProductAddDto productAddDto,
            Authentication authentication
    ) {

        this.productService.addProduct(
                productAddDto,
                authenticationValidator.validateAndGetName(authentication)
        );

        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "상품 등록 성공",
                null
        );
    }

    @PutMapping
    public ResponseEntity<HttpResponseDto> updateProduct(
            @RequestBody ProductUpdateDto productUpdateDto,
            Authentication authentication
    ) {
        this.productService.updateProduct(
                productUpdateDto,
                authenticationValidator.validateAndGetName(authentication)
        );

        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "상품 수정 성공",
                null
        );
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<HttpResponseDto> deleteProduct(
            @PathVariable Long id,
            Authentication authentication
    ) {
        this.productService.deleteProduct(
                id,
                authenticationValidator.validateAndGetName(authentication)
        );

        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "상품 제거 성공",
                null
        );
    }
}