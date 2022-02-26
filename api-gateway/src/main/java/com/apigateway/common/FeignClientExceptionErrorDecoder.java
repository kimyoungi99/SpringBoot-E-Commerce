package com.apigateway.common;

import com.apigateway.exception.FeignClientException;
import com.apigateway.mapper.MapToResponseDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {
    private ObjectMapper objectMapper = new ObjectMapper();
    private StringDecoder stringDecoder = new StringDecoder();

    @Override
    public FeignClientException decode(String methodKey, Response response) {
        String message = null;
        Map<String, Object> map = null;
        if (response.body() != null) {
            try {
                message = stringDecoder.decode(response, String.class).toString();
                map = objectMapper.readValue(message, Map.class);
            } catch (IOException e) {
                log.error(methodKey + "Error Deserializing response body from failed feign request response.", e);
            }
        }
        return new FeignClientException(MapToResponseDtoMapper.map(map).getMessage());
    }
}
