package com.ecommerce.servercommon.domain.product;

import com.ecommerce.servercommon.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
@AllArgsConstructor
public class Product {
    private Long id;
    private Long sellerId;
    private String name;
    private Long price;

    public ProductResponseDto toResponseDto() {
        return ProductResponseDto.builder()
                .id(this.id)
                .sellerId(this.sellerId)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
