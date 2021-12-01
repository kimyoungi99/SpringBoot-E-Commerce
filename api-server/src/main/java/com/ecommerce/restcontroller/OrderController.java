package com.ecommerce.restcontroller;

import com.ecommerce.common.config.security.AuthenticationValidator;
import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.common.response.ResponseBuilder;
import com.ecommerce.servercommon.dto.OrderDto;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                authenticationValidator.validateAndGetName(authentication)
        );

        return responseBuilder.jsonResponseBuild(
          HttpStatus.OK,
          "주문 요청 성공",
          null
        );
    }
}
