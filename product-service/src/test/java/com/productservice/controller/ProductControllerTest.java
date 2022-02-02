package com.productservice.controller;

import com.productservice.dao.ProductDao;
import com.productservice.domain.ProductEntity;
import com.productservice.dto.ProductAddDto;
import com.productservice.dto.ProductDeleteDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.dto.ResponseDto;
import com.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest {

    @Mock
    private ProductDao productDao;

    private ProductController productController;

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
        this.productController = new ProductController(new ProductService(this.productDao));
    }

    @Test
    @DisplayName("add test")
    void addTest() {
        ArgumentCaptor<ProductEntity> productEntityArgumentCaptor
                = ArgumentCaptor.forClass(ProductEntity.class);

        this.productController.add(ProductAddDto.builder()
                .categoryName("asdf")
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

        this.productController.delete(ProductDeleteDto.builder()
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

        ResponseEntity<ResponseDto> response = this.productController.info(targetId);

        checkSameProductResponseDto((ProductResponseDto) response.getBody().getData(), this.productEntity1.toResponseDto());
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