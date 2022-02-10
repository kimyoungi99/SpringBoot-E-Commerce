package com.orderservice.controller;

import com.orderservice.dto.OrderAddDto;
import com.orderservice.dto.OrderAddResultResponse;
import com.orderservice.dto.ResponseDto;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    public ResponseEntity<ResponseDto> order(@RequestBody OrderAddDto orderAddDto) {
        OrderAddResultResponse resultResponse = this.orderService.order(orderAddDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(resultResponse)
                        .build()
                );
    }
}
