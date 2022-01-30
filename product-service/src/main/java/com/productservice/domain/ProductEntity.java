package com.productservice.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class ProductEntity {

    @Id
    private String id;

    private String name;

    private Long stock;

    private Long totalSales;

    private Long price;

    private String categoryId;

    private String categoryName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdDate;

    @Builder
    public ProductEntity(String id, String name, Long stock, Long totalSales, Long price, String categoryId, String categoryName, Date createdDate) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.totalSales = totalSales;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
    }
}
