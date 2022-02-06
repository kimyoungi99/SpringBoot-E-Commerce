package com.productservice.service;

import com.productservice.client.CategoryServiceClient;
import com.productservice.dao.ProductDao;
import com.productservice.domain.ProductEntity;
import com.productservice.dto.*;
import com.productservice.exception.KafkaConnectionException;
import com.productservice.exception.ProductNotExistingException;
import com.productservice.mapper.MapToCategoryResponseDtoMapper;
import com.productservice.mapper.MapToResponseDtoMapper;
import com.productservice.mapper.ProductAddDtoToProductEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;
    private final ProductDao productDao;
    private final CategoryServiceClient categoryServiceClient;
    private final String productAddDeleteTopicName;


    public void insert(ProductAddDto productAddDto) {
        ProductEntity productEntity = ProductAddDtoToProductEntityMapper.map(productAddDto);

        // init?
        // 에러 처리 필요
        ResponseDto response = MapToResponseDtoMapper.map(
                this.categoryServiceClient.info(productAddDto.getCategoryId())
        );
        productEntity.setCategoryName(MapToCategoryResponseDtoMapper.map((Map<String, Object>) response.getData()).getName());
        productEntity.setCreatedDate(new Date());
        productEntity.setTotalSales(0L);

        String productId = productDao.insert(productEntity);

        KafkaMessageDto kafkaMessageDto = KafkaMessageDto.builder()
                .domain("ProductService")
                .eventType("ProductAddEvent")
                .data(ProductAddMessageDto.builder()
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
        String productId = this.productDao.deleteById(productDeleteDto.getId());

        KafkaMessageDto kafkaMessageDto = KafkaMessageDto.builder()
                .domain("ProductService")
                .eventType("ProductDeleteEvent")
                .data(productDeleteDto)
                .build();
        try {
            this.kafkaTemplate.send(this.productAddDeleteTopicName, kafkaMessageDto);
            log.info("상품 삭제 성공 : " + "(id : " + productId);
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

    public void updateSellerEmail(EmailUpdateDto emailUpdateDto) {
        this.productDao.updateSellerEmail(emailUpdateDto.getId(), emailUpdateDto.getEmail());
    }

    public void updateCategoryName(CategoryUpdateDto categoryUpdateDto) {
        this.productDao.updateCategoryName(categoryUpdateDto.getId(), categoryUpdateDto.getName());
    }
}
