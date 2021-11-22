package com.ecommerce.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class HttpResponseDto {
    private HttpStatus status;
    private String message;
    private Object data;
}
