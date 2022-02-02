package com.productservice.domain;

import com.productservice.dto.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class ProductEntity {

    @Id
    private String id;

    private String sellerId;

    private String sellerEmail;

    private String name;

    private Long stock;

    private Long totalSales;

    private Long price;

    private String categoryId;

    private String categoryName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdDate;

    @Builder

    public ProductEntity(String id, String sellerId, String sellerEmail, String name, Long stock, Long totalSales, Long price, String categoryId, String categoryName, Date createdDate) {
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

    public void setTotalSales(Long totalSales) {
        this.totalSales = totalSales;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ProductResponseDto toResponseDto() {
        return ProductResponseDto.builder()
                .sellerId(this.sellerId)
                .sellerEmail(this.sellerEmail)
                .name(this.name)
                .stock(this.stock)
                .price(this.price)
                .totalSales(this.totalSales)
                .categoryId(this.categoryId)
                .categoryName(this.categoryName)
                .createdDate(this.createdDate)
                .build();
    }
}
