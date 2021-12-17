package com.ecommerce.servercommon.dto;

import com.ecommerce.servercommon.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductUpdateDto {

    private Long id;
    private String name;
    private Long price;
    private Long stock;

    public Product toProductEntity() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
