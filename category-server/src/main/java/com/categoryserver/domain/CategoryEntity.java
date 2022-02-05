package com.categoryserver.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class CategoryEntity {

    @Id
    private String id;

    private String name;

    @Builder
    public CategoryEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
