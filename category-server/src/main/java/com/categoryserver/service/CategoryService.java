package com.categoryserver.service;

import com.categoryserver.dao.CategoryDao;
import com.categoryserver.domain.CategoryEntity;
import com.categoryserver.dto.CategoryAddDto;
import com.categoryserver.dto.CategoryResponseDto;
import com.categoryserver.exception.CategoryNotExistingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;

    public List<CategoryResponseDto> getAll() {
        return this.categoryDao.findAll().stream().map(CategoryEntity::toResponseDto).collect(Collectors.toList());
    }

    public void insert(CategoryAddDto categoryAddDto) {
        // init?
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(categoryAddDto.getName())
                .count(0L)
                .build();

        this.categoryDao.insert(categoryEntity);
    }

    public CategoryResponseDto info(String id) {
        Optional<CategoryEntity> optionalCategoryEntity = this.categoryDao.findById(id);

        CategoryEntity categoryEntity =
                optionalCategoryEntity.orElseThrow(
                        () -> new CategoryNotExistingException("존재하지 않는 카태고리 오류.")
                );

        return categoryEntity.toResponseDto();
    }
}
