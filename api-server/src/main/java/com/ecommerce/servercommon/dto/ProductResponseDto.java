package com.ecommerce.servercommon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    private Long id;
    private Long sellerId;
    private String name;
    private Long price;
}
