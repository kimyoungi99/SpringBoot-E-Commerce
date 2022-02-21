package com.apigateway.controller;

import com.apigateway.dto.OrderAddDto;
import com.apigateway.dto.ResponseDto;
import com.apigateway.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    public ResponseEntity<ResponseDto> order(
            @RequestHeader(value = "Authentication") String token,
            @RequestBody OrderAddDto orderAddDto
    ) {

        ResponseDto responseDto = this.orderService.order(token, orderAddDto);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @GetMapping(value = "/getOrderList")
    public ResponseEntity<ResponseDto> getOrderList(@RequestHeader(value = "Authentication") String token) {

        ResponseDto responseDto = this.orderService.getOrderList(token);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @GetMapping(value = "/getSellList")
    public ResponseEntity<ResponseDto> getSellList(@RequestHeader(value = "Authentication") String token) {
        ResponseDto responseDto = this.orderService.getSellList(token);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }
}
