package com.categoryserver.controller;

import com.categoryserver.dao.CategoryDao;
import com.categoryserver.domain.CategoryEntity;
import com.categoryserver.dto.*;
import com.categoryserver.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryControllerTest {

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;

    private CategoryController categoryController;

    // Test Data
    private CategoryEntity categoryEntity1;
    private String kafkaTopicName = "asdf";

    @BeforeEach
    public void init() {
        this.categoryEntity1 = CategoryEntity.builder()
                .name("asdf")
                .count(0L)
                .build();

        MockitoAnnotations.openMocks(this);
        this.categoryController = new CategoryController(new CategoryService(this.kafkaTemplate, this.categoryDao, this.kafkaTopicName));
    }

    @Test
    @DisplayName("insert test")
    void insertTest() {
        ArgumentCaptor<CategoryEntity> categoryEntityArgumentCaptor
                = ArgumentCaptor.forClass(CategoryEntity.class);

        this.categoryController.add(CategoryAddDto.builder()
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

        ResponseEntity<ResponseDto> response = this.categoryController.info(this.categoryEntity1.getId());

        checkSameCategoryResponseDto((CategoryResponseDto) response.getBody().getData(), this.categoryEntity1.toResponseDto());
    }

    @Test
    @DisplayName("getAll test")
    void fetAllTest() {
        Mockito.when(this.categoryDao.findAll())
                .thenReturn(Arrays.asList(this.categoryEntity1));

        ResponseEntity<ResponseDto> response = this.categoryController.all();

        checkSameCategoryResponseDto(((List<CategoryResponseDto>) response.getBody().getData()).get(0), this.categoryEntity1.toResponseDto());
    }

    @Test
    @DisplayName("update 테스트")
    public void updateTest() {
        Mockito.when(this.categoryDao.findById(this.categoryEntity1.getId()))
                .thenReturn(Optional.ofNullable(this.categoryEntity1));
        ArgumentCaptor<KafkaMessageDto> kafkaMessageDtoArgumentCaptor
                = ArgumentCaptor.forClass(KafkaMessageDto.class);
        ArgumentCaptor<CategoryEntity> categoryEntityArgumentCaptor
                = ArgumentCaptor.forClass(CategoryEntity.class);
        ArgumentCaptor<String> stringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .id(this.categoryEntity1.getId())
                .name("akmu")
                .build();


        this.categoryController.update(categoryUpdateDto);

        Mockito.verify(this.categoryDao).update(categoryEntityArgumentCaptor.capture());
        Mockito.verify(this.kafkaTemplate).send(stringArgumentCaptor.capture(), kafkaMessageDtoArgumentCaptor.capture());

        assertThat(categoryUpdateDto.getId()).isEqualTo(categoryEntityArgumentCaptor.getValue().getId());
        assertThat(categoryUpdateDto.getName()).isEqualTo(categoryEntityArgumentCaptor.getValue().getName());

        assertThat(this.kafkaTopicName).isEqualTo(stringArgumentCaptor.getValue());
        assertThat(categoryUpdateDto.getName()).isEqualTo(
                ((CategoryUpdateDto) kafkaMessageDtoArgumentCaptor.getValue().getData()).getName()
        );
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