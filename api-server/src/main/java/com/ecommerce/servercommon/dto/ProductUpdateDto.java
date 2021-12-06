package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.product.Product;
import lombok.Data;

@Data
public class ProductUpdateDto {

    private Long id;
    private String name;
    private Long price;

    public Product toEntity() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .build();
    }
}