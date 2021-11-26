package com.ecommerce.servercommon.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Long price;
}
