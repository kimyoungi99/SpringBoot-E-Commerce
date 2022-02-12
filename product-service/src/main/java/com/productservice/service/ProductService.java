package com.productservice.service;

import com.productservice.client.CategoryServiceClient;
import com.productservice.client.UserServiceClient;
import com.productservice.dao.ProductDao;
import com.productservice.domain.ProductEntity;
import com.productservice.dto.*;
import com.productservice.exception.KafkaConnectionException;
import com.productservice.exception.ProductNotExistingException;
import com.productservice.mapper.MapToCategoryResponseDtoMapper;
import com.productservice.mapper.MapToEmailResponseDtoMapper;
import com.productservice.mapper.MapToResponseDtoMapper;
import com.productservice.mapper.ProductAddDtoToProductEntityMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private final KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;
    private final ProductDao productDao;
    private final CategoryServiceClient categoryServiceClient;
    private final UserServiceClient userServiceClient;
    private final String productAddDeleteTopicName;


    public void insert(ProductAddDto productAddDto) {
        ProductEntity productEntity = ProductAddDtoToProductEntityMapper.map(productAddDto);

        // init?
        // 에러 처리 필요
        ResponseDto categoryResponse = MapToResponseDtoMapper.map(
                this.categoryServiceClient.info(productAddDto.getCategoryId())
        );
        productEntity.setCategoryName(MapToCategoryResponseDtoMapper.map((Map<String, Object>) categoryResponse.getData()).getName());

        ResponseDto emailResponse = MapToResponseDtoMapper.map(
                this.userServiceClient.getEmail(productAddDto.getSellerId())
        );
        productEntity.setSellerEmail(MapToEmailResponseDtoMapper.map((Map<String, Object>) emailResponse.getData()).getEmail());

        productEntity.setCreatedDate(new Date());
        productEntity.setTotalSales(0L);

        String productId = productDao.insert(productEntity);

        KafkaMessageDto kafkaMessageDto = KafkaMessageDto.builder()
                .domain("ProductService")
                .eventType("ProductAddEvent")
                .data(ProductAddDeleteMessageDto.builder()
                        .id(productId)
                        .name(productAddDto.getName())
                        .price(productAddDto.getPrice())
                        .sellerId(productAddDto.getSellerId())
                        .categoryId(productAddDto.getCategoryId())
                        .stock(productAddDto.getStock())
                        .build()
                )
                .build();
        try {
            this.kafkaTemplate.send(this.productAddDeleteTopicName, kafkaMessageDto);
            log.info("상품 생성 성공 : " + "(id : " + productId + ", name : " + productEntity.getName() + ")");
        }
        catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new KafkaConnectionException("카프카 응답 오류.");
        }
    }

    public void delete(ProductDeleteDto productDeleteDto) {
        Optional<ProductEntity> optionalProductEntity = this.productDao.findAndRemove(productDeleteDto.getId());

        ProductEntity productEntity =
                optionalProductEntity.orElseThrow(
                        () -> new ProductNotExistingException("존재하지 않는 상품 오류.")
                );

        KafkaMessageDto kafkaMessageDto = KafkaMessageDto.builder()
                .domain("ProductService")
                .eventType("ProductDeleteEvent")
                .data(ProductAddDeleteMessageDto.builder()
                        .id(productEntity.getId())
                        .name(productEntity.getName())
                        .price(productEntity.getPrice())
                        .sellerId(productEntity.getSellerId())
                        .categoryId(productEntity.getCategoryId())
                        .stock(productEntity.getStock())
                        .build()
                )
                .build();
        try {
            this.kafkaTemplate.send(this.productAddDeleteTopicName, kafkaMessageDto);
            log.info("상품 삭제 성공 : " + "(id : " + productEntity.getId() + ")");
        }
        catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new KafkaConnectionException("카프카 응답 오류.");
        }
    }

    public ProductResponseDto info(String id) {
        Optional<ProductEntity> optionalProductEntity = this.productDao.findById(id);

        ProductEntity productEntity =
                optionalProductEntity.orElseThrow(
                        () -> new ProductNotExistingException("존재하지 않는 상품 오류.")
                );

        return productEntity.toResponseDto();
    }

    public ProductForOrderDto infoForOrder(String id) {
        Optional<ProductEntity> optionalProductEntity = this.productDao.findById(id);

        ProductEntity productEntity =
                optionalProductEntity.orElseThrow(
                        () -> new ProductNotExistingException("존재하지 않는 상품 오류.")
                );

        return ProductForOrderDto.builder()
                .id(productEntity.getId())
                .price(productEntity.getPrice())
                .name(productEntity.getName())
                .sellerId(productEntity.getSellerId())
                .build();
    }

    public void updateSellerEmail(EmailUpdateDto emailUpdateDto) {
        this.productDao.updateSellerEmail(emailUpdateDto.getId(), emailUpdateDto.getEmail());
    }

    public void updateCategoryName(CategoryUpdateDto categoryUpdateDto) {
        this.productDao.updateCategoryName(categoryUpdateDto.getId(), categoryUpdateDto.getName());
    }

    public void updateStock(StockUpdateDto stockUpdateDto) {
        this.productDao.updateStock(stockUpdateDto.getProductId(), stockUpdateDto.getQuantity());
    }

    @Builder
    public ProductService(KafkaTemplate<String, KafkaMessageDto> kafkaTemplate, ProductDao productDao, CategoryServiceClient categoryServiceClient, UserServiceClient userServiceClient, String productAddDeleteTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.productDao = productDao;
        this.categoryServiceClient = categoryServiceClient;
        this.userServiceClient = userServiceClient;
        this.productAddDeleteTopicName = productAddDeleteTopicName;
    }
}
