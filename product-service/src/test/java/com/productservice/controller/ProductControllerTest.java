package com.productservice.controller;

import com.productservice.client.CategoryServiceClient;
import com.productservice.client.UserServiceClient;
import com.productservice.dao.ProductDao;
import com.productservice.domain.ProductEntity;
import com.productservice.dto.*;
import com.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private CategoryServiceClient categoryServiceClient;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;

    private ProductController productController;

    // Test Data
    private ProductEntity productEntity1;
    private String productAddDeleteTopicName = "asdfadddelete";

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
        this.productController = new ProductController(ProductService.builder()
                .userServiceClient(this.userServiceClient)
                .productAddDeleteTopicName(this.productAddDeleteTopicName)
                .productDao(this.productDao)
                .kafkaTemplate(this.kafkaTemplate)
                .categoryServiceClient(this.categoryServiceClient)
                .build()
        );
    }

    @Test
    @DisplayName("추가 테스트")
    void addTest() {
        ArgumentCaptor<ProductEntity> productEntityArgumentCaptor
                = ArgumentCaptor.forClass(ProductEntity.class);
        ArgumentCaptor<String> stringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<KafkaMessageDto> kafkaMessageDtoArgumentCaptor
                = ArgumentCaptor.forClass(KafkaMessageDto.class);

        Mockito.when(this.categoryServiceClient.info(this.productEntity1.getCategoryId()))
                .thenReturn(Stream.of(
                                new AbstractMap.SimpleImmutableEntry<>("status", "OK"),
                                new AbstractMap.SimpleImmutableEntry<>("dateTime", "2022-02-05T21:38:29.807704"),
                                new AbstractMap.SimpleImmutableEntry<>("message", "OK"),
                                new AbstractMap.SimpleImmutableEntry<>("data", Stream.of(
                                        new AbstractMap.SimpleImmutableEntry<>("name", this.productEntity1.getName())
                                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                                )
                        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                );
        Mockito.when(this.userServiceClient.getEmail(this.productEntity1.getSellerId()))
                .thenReturn(Stream.of(
                                new AbstractMap.SimpleImmutableEntry<>("status", "OK"),
                                new AbstractMap.SimpleImmutableEntry<>("dateTime", "2022-02-05T21:38:29.807704"),
                                new AbstractMap.SimpleImmutableEntry<>("message", "OK"),
                                new AbstractMap.SimpleImmutableEntry<>("data", Stream.of(
                                        new AbstractMap.SimpleImmutableEntry<>("email", this.productEntity1.getSellerEmail())
                                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                                )
                        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                );

        this.productController.add(ProductAddDto.builder()
                .categoryId(this.productEntity1.getCategoryId())
                .name(this.productEntity1.getName())
                .price(this.productEntity1.getPrice())
                .stock(this.productEntity1.getStock())
                .sellerId(this.productEntity1.getSellerId())
                .build()
        );
        Mockito.verify(this.productDao).insert(productEntityArgumentCaptor.capture());
        Mockito.verify(this.kafkaTemplate).send(stringArgumentCaptor.capture(), kafkaMessageDtoArgumentCaptor.capture());

        checkSameProductEntityWithoutCreatedTime(this.productEntity1, productEntityArgumentCaptor.getValue());
        assertThat(this.productAddDeleteTopicName).isEqualTo(stringArgumentCaptor.getValue());
        assertThat(kafkaMessageDtoArgumentCaptor.getValue().getEventType()).isEqualTo("ProductAddEvent");
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteTest() {
        ArgumentCaptor<String> stringArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        String targetId = "Asdf";
        ArgumentCaptor<String> stringTopicArgumentCaptor
                = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<KafkaMessageDto> kafkaMessageDtoArgumentCaptor
                = ArgumentCaptor.forClass(KafkaMessageDto.class);
        Mockito.when(this.productDao.findAndRemove(targetId)).thenReturn(Optional.ofNullable(this.productEntity1));

        this.productController.delete(ProductDeleteDto.builder()
                .id(targetId)
                .build());
        Mockito.verify(this.productDao).findAndRemove(stringArgumentCaptor.capture());
        Mockito.verify(this.kafkaTemplate).send(stringTopicArgumentCaptor.capture(), kafkaMessageDtoArgumentCaptor.capture());

        assertThat(targetId).isEqualTo(stringArgumentCaptor.getValue());
        assertThat(this.productAddDeleteTopicName).isEqualTo(stringTopicArgumentCaptor.getValue());
        assertThat(kafkaMessageDtoArgumentCaptor.getValue().getEventType()).isEqualTo("ProductDeleteEvent");
    }

    @Test
    @DisplayName("조회 테스트")
    void infoTest() {
        String targetId = "asdf";
        Mockito.when(this.productDao.findById(targetId)).thenReturn(Optional.ofNullable(this.productEntity1));

        ResponseEntity<ResponseDto> response = this.productController.info(targetId);

        checkSameProductResponseDto((ProductResponseDto) response.getBody().getData(), this.productEntity1.toResponseDto());
    }

    @Test
    @DisplayName("가격 조회 테스트")
    void priceTest() {
        String targetId = "asdf";
        Mockito.when(this.productDao.findById(targetId)).thenReturn(Optional.ofNullable(this.productEntity1));

        ResponseEntity<ResponseDto> response = this.productController.infoForOrder(targetId);

        assertThat(((ProductForOrderDto) response.getBody().getData()).getPrice()).isEqualTo(this.productEntity1.getPrice());
        assertThat(((ProductForOrderDto) response.getBody().getData()).getName()).isEqualTo(this.productEntity1.getName());
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