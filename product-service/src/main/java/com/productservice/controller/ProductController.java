package com.productservice.controller;

import com.productservice.dto.*;
import com.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-service")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseDto> add(@RequestBody ProductAddDto productAddDto) {
        this.productService.insert(productAddDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .build()
                );
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<ResponseDto> delete(@RequestBody ProductDeleteDto productDeleteDto) {
        this.productService.delete(productDeleteDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .build()
                );
    }

    @GetMapping(value = "/info/{id}")
    public ResponseEntity<ResponseDto> info(@PathVariable String id) {
        ProductResponseDto info = this.productService.info(id);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(info)
                        .build()
                );
    }

    @GetMapping(value = "/infoForOrder/{id}")
    public ResponseEntity<ResponseDto> infoForOrder(@PathVariable String id) {
        ProductForOrderDto price = this.productService.infoForOrder(id);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(price)
                        .build()
                );
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<ResponseDto> getAll() {
        List<ProductSimpleResponseDto> productSimpleResponseDtos = this.productService.getAll();

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(productSimpleResponseDtos)
                        .build()
                );
    }
}
