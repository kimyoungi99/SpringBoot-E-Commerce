package com.categoryserver.service;

import com.categoryserver.dao.CategoryDao;
import com.categoryserver.domain.CategoryEntity;
import com.categoryserver.dto.CategoryAddDto;
import com.categoryserver.dto.CategoryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryDao categoryDao;

    private CategoryService categoryService;

    // Test Data
    private CategoryEntity categoryEntity1;

    @BeforeEach
    public void init() {
        this.categoryEntity1 = CategoryEntity.builder()
                .name("asdf")
                .count(0L)
                .build();

        MockitoAnnotations.openMocks(this);
        this.categoryService = new CategoryService(this.categoryDao);
    }

    @Test
    @DisplayName("insert test")
    void insertTest() {
        ArgumentCaptor<CategoryEntity> categoryEntityArgumentCaptor
                = ArgumentCaptor.forClass(CategoryEntity.class);

        this.categoryService.insert(CategoryAddDto.builder()
                .name(this.categoryEntity1.getName())
                .build()
        );
        Mockito.verify(this.categoryDao).insert(categoryEntityArgumentCaptor.capture());

        checkSameCategoryEntity(this.categoryEntity1, categoryEntityArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("info test")
    void infoTest() {
        Mockito.when(this.categoryDao.findById(this.categoryEntity1.getId()))
                .thenReturn(Optional.ofNullable(this.categoryEntity1));

        CategoryResponseDto categoryResponseDto = this.categoryService.info(this.categoryEntity1.getId());

        checkSameCategoryResponseDto(categoryResponseDto, this.categoryEntity1.toResponseDto());
    }

    @Test
    @DisplayName("getAll test")
    void fetAllTest() {
        Mockito.when(this.categoryDao.findAll())
                .thenReturn(Arrays.asList(this.categoryEntity1));

        List<CategoryResponseDto> all = this.categoryService.getAll();

        checkSameCategoryResponseDto(all.get(0), this.categoryEntity1.toResponseDto());
    }

    private void checkSameCategoryEntity(CategoryEntity categoryEntity1, CategoryEntity categoryEntity2) {
        assertThat(categoryEntity1.getId()).isEqualTo(categoryEntity2.getId());
        assertThat(categoryEntity1.getName()).isEqualTo(categoryEntity2.getName());
        assertThat(categoryEntity1.getCount()).isEqualTo(categoryEntity2.getCount());
    }

    private void checkSameCategoryResponseDto(CategoryResponseDto categoryResponseDto1, CategoryResponseDto categoryResponseDto2) {
        assertThat(categoryResponseDto1.getId()).isEqualTo(categoryResponseDto2.getId());
        assertThat(categoryResponseDto1.getName()).isEqualTo(categoryResponseDto2.getName());
        assertThat(categoryResponseDto1.getCount()).isEqualTo(categoryResponseDto2.getCount());
    }
}