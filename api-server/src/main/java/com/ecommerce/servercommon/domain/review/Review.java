package com.ecommerce.servercommon.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @Builder
@AllArgsConstructor
public class Review {
    private Long id;
    private Long orderId;
    private Long reviewerId;
    private BigDecimal star;
    private String review;
}
