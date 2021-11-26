package com.ecommerce.restcontroller;

import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.servercommon.dto.OrderDto;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public HttpResponseDto order(@RequestBody OrderDto orderDto) {
        this.orderService.sendOrderMessage(orderDto);
        return new HttpResponseDto(
                HttpStatus.ACCEPTED,
                "주문 요청 완료",
                null
        );
    }
}
