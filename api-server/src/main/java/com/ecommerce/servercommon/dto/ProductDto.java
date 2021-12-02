package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.product.Product;
import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private Long price;

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .build();
    }
}
