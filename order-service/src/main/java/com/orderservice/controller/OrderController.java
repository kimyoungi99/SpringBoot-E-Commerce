package com.orderservice.controller;

import com.orderservice.dto.OrderAddDto;
import com.orderservice.dto.OrderAddResultResponseDto;
import com.orderservice.dto.OrderResponseDto;
import com.orderservice.dto.ResponseDto;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    public ResponseEntity<ResponseDto> order(@RequestBody OrderAddDto orderAddDto) {
        OrderAddResultResponseDto resultResponse = this.orderService.order(orderAddDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(resultResponse)
                        .build()
                );
    }

    @GetMapping(value = "/getOrderList/{buyerId}")
    public ResponseEntity<ResponseDto> getOrderList(@PathVariable String buyerId) {
        List<OrderResponseDto> orderList =
                this.orderService.getOrderList(buyerId);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(orderList)
                        .build()
                );
    }

    @GetMapping(value = "/getSellList/{sellerId}")
    public ResponseEntity<ResponseDto> getSellList(@PathVariable String sellerId) {
        List<OrderResponseDto> sellList = this.orderService.getSellList(sellerId);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(sellList)
                        .build()
                );
    }
}
