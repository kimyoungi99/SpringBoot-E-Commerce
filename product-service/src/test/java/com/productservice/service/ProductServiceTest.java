package com.productservice.service;

import com.productservice.client.CategoryServiceClient;
import com.productservice.dao.ProductDao;
import com.productservice.domain.ProductEntity;
import com.productservice.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private CategoryServiceClient categoryServiceClient;

    private ProductService productService;

    // Test Data
    private ProductEntity productEntity1;

    @BeforeEach
    public void init() {
        this.productEntity1 = ProductEntity.builder()
                .categoryName("asdf")
                .categoryId("asdf")
                .name("Asdf")
                .price(1L)
                .totalSales(0L)
                .stock(10L)
                .sellerId("sadf")
                .sellerEmail("qaxwscedv")
                .build();
        MockitoAnnotations.openMocks(this);
        this.productService = new ProductService(this.productDao, this.categoryServiceClient);
    }

    @Test
    @DisplayName("insert test")
    void insertTest() {
        ArgumentCaptor<ProductEntity> productEntityArgumentCaptor
                = ArgumentCaptor.forClass(ProductEntity.class);
        Mockito.when(this.categoryServiceClient.info(this.productEntity1.getCategoryId()))
                .thenReturn(Stream.of(
                                new AbstractMap.SimpleImmutableEntry<>("name", this.productEntity1.getName()),
                                new AbstractMap.SimpleImmutableEntry<>("status", "OK"),
                                new AbstractMap.SimpleImmutableEntry<>("dateTime", "2022-02-05T21:38:29.807704"),
                                new AbstractMap.SimpleImmutableEntry<>("message", "OK"),
                                new AbstractMap.SimpleImmutableEntry<>("data", Stream.of(
                                        new AbstractMap.SimpleImmutableEntry<>("name", this.productEntity1.getName())
                                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                                )
                        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                );

        this.productService.insert(ProductAddDto.builder()
                .categoryId("asdf")
                .name("Asdf")
                .price(1L)
                .stock(10L)
                .sellerId("sadf")
                .sellerEmail("qaxwscedv")
                .build()
        );
        Mockito.verify(this.productDao).insert(productEntityArgumentCaptor.capture());

        checkSameProductEntityWithoutCreatedTime(this.productEntity1, productEntityArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("delete test")
    void deleteTest() {
        ArgumentCaptor<String> stringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        String targetId = "Asdf";

        this.productService.delete(ProductDeleteDto.builder()
                .id(targetId)
                .build());
        Mockito.verify(this.productDao).deleteById(stringArgumentCaptor.capture());

        assertThat(targetId).isEqualTo(stringArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("info test")
    void infoTest() {
        String targetId = "asdf";
        Mockito.when(this.productDao.findById(targetId)).thenReturn(Optional.ofNullable(this.productEntity1));

        ProductResponseDto productResponseDto = this.productService.info(targetId);

        checkSameProductResponseDto(productResponseDto, this.productEntity1.toResponseDto());
    }

    @Test
    @DisplayName("판매자 이메일 수정 테스트")
    void emailUpdateTest() {
        ArgumentCaptor<String> idStringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> emailStringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        EmailUpdateDto emailUpdateDto = EmailUpdateDto.builder()
                .id("ASdf")
                .email("ASdf")
                .build();


        this.productService.updateSellerEmail(emailUpdateDto);
        Mockito.verify(this.productDao).updateSellerEmail(idStringArgumentCaptor.capture(), emailStringArgumentCaptor.capture());

        assertThat(emailUpdateDto.getEmail()).isEqualTo(emailStringArgumentCaptor.getValue());
        assertThat(emailUpdateDto.getId()).isEqualTo(idStringArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("카테고리 이름 수정 테스트")
    void categoryNameUpdateTest() {
        ArgumentCaptor<String> categoryIdStringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> categoryNameStringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .id(this.productEntity1.getCategoryId())
                .name(this.productEntity1.getCategoryName())
                .build();


        this.productService.updateCategoryName(categoryUpdateDto);
        Mockito.verify(this.productDao).updateCategoryName(categoryIdStringArgumentCaptor.capture(), categoryNameStringArgumentCaptor.capture());

        assertThat(this.productEntity1.getCategoryId()).isEqualTo(categoryIdStringArgumentCaptor.getValue());
        assertThat(this.productEntity1.getCategoryName()).isEqualTo(categoryNameStringArgumentCaptor.getValue());
    }

    private void checkSameProductEntityWithoutCreatedTime(ProductEntity productEntity1, ProductEntity productEntity2) {
        assertThat(productEntity1.getId()).isEqualTo(productEntity2.getId());
        assertThat(productEntity1.getName()).isEqualTo(productEntity2.getName());
        assertThat(productEntity1.getTotalSales()).isEqualTo(productEntity2.getTotalSales());
        assertThat(productEntity1.getStock()).isEqualTo(productEntity2.getStock());
        assertThat(productEntity1.getTotalSales()).isEqualTo(productEntity2.getTotalSales());
        assertThat(productEntity1.getPrice()).isEqualTo(productEntity2.getPrice());
        assertThat(productEntity1.getCategoryName()).isEqualTo(productEntity2.getCategoryId());
        assertThat(productEntity1.getCategoryId()).isEqualTo(productEntity2.getCategoryId());
        assertThat(productEntity1.getSellerEmail()).isEqualTo(productEntity2.getSellerEmail());
        assertThat(productEntity1.getTotalSales()).isEqualTo(productEntity2.getTotalSales());
    }

    private void checkSameProductResponseDto(ProductResponseDto productResponseDto1, ProductResponseDto productResponseDto2) {
        assertThat(productResponseDto1.getId()).isEqualTo(productResponseDto2.getId());
        assertThat(productResponseDto1.getCreatedDate()).isEqualTo(productResponseDto2.getCreatedDate());
        assertThat(productResponseDto1.getPrice()).isEqualTo(productResponseDto2.getPrice());
        assertThat(productResponseDto1.getCategoryId()).isEqualTo(productResponseDto2.getCategoryId());
        assertThat(productResponseDto1.getName()).isEqualTo(productResponseDto2.getName());
        assertThat(productResponseDto1.getStock()).isEqualTo(productResponseDto2.getStock());
        assertThat(productResponseDto1.getSellerEmail()).isEqualTo(productResponseDto2.getSellerEmail());
        assertThat(productResponseDto1.getTotalSales()).isEqualTo(productResponseDto2.getTotalSales());
    }
}