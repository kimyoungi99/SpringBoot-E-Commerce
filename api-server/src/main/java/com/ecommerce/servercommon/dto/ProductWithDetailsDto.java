package com.ecommerce.servercommon.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductWithDetailsDto {
    private Long id;
    private Long sellerId;
    private String name;
    private Long price;
    private Long detailsId;
    private BigDecimal rating;
    private Long sellCount;
    private Long reviewCount;
    private Long stock;
}
