package com.productservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProductResponseDto {
    private String id;

    private String sellerId;

    private String sellerEmail;

    private String name;

    private Long stock;

    private Long totalSales;

    private Long price;

    private String categoryId;

    private String categoryName;

    private Date createdDate;

    @Builder
    public ProductResponseDto(String id, String sellerId, String sellerEmail, String name, Long stock, Long totalSales, Long price, String categoryId, String categoryName, Date createdDate) {
        this.id = id;
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.name = name;
        this.stock = stock;
        this.totalSales = totalSales;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
    }
}
