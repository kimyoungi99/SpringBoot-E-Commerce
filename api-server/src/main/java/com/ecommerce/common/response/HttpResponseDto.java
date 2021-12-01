package com.ecommerce.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseDto {
    private HttpStatus HttpStatus;
    private String message;
    private Object data;
}
