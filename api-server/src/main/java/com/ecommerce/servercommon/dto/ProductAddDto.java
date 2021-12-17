package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddDto {
    private String name;
    private Long price;
    private Long stock;

    public Product toProductEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .build();
    }
}
