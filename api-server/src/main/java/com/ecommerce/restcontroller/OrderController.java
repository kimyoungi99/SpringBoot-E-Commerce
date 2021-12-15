package com.ecommerce.restcontroller;

import com.ecommerce.common.security.AuthenticationValidator;
import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.common.response.ResponseBuilder;
import com.ecommerce.servercommon.dto.OrderDto;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(value = "/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthenticationValidator authenticationValidator;
    private final ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<HttpResponseDto> order(
            Authentication authentication,
            @RequestBody OrderDto orderDto
    ) throws AuthenticationException {

        this.orderService.sendOrderMessage(
                orderDto,
                this.authenticationValidator.validateAndGetName(authentication)
        );

        return this.responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "주문 요청 성공",
                null
        );
    }

    @GetMapping
    public ResponseEntity<HttpResponseDto> getAllOrder(
            Authentication authentication
            ) throws AuthenticationException {

        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "주문 조회 성공",
                this.orderService.getAllOrder(
                        this.authenticationValidator.validateAndGetName(authentication)
                )
        );
    }
}
