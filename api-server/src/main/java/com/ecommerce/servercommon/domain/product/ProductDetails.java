package com.ecommerce.servercommon.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @Builder
@AllArgsConstructor
public class ProductDetails {
    private Long id;
    private Long productId;
    private BigDecimal rating;
    private Long sellCount;
    private Long reviewCount;
    private Long stock;
}
