package com.productservice.mapper;

import com.productservice.domain.ProductEntity;
import com.productservice.dto.ProductAddDto;

public class ProductAddDtoToProductEntityMapper {

    public static ProductEntity map(ProductAddDto productAddDto) {
        return ProductEntity.builder()
                .sellerId(productAddDto.getSellerId())
                .sellerEmail(productAddDto.getSellerEmail())
                .name(productAddDto.getName())
                .stock(productAddDto.getStock())
                .price(productAddDto.getPrice())
                .categoryId(productAddDto.getCategoryId())
                .categoryName(productAddDto.getCategoryName())
                .build();
    }
}
