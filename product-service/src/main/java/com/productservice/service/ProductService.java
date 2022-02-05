package com.productservice.service;

import com.productservice.client.CategoryServiceClient;
import com.productservice.dao.ProductDao;
import com.productservice.domain.ProductEntity;
import com.productservice.dto.*;
import com.productservice.exception.ProductNotExistingException;
import com.productservice.mapper.MapToCategoryResponseDtoMapper;
import com.productservice.mapper.MapToResponseDtoMapper;
import com.productservice.mapper.ProductAddDtoToProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final CategoryServiceClient categoryServiceClient;

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

        productDao.insert(productEntity);
    }

    public void delete(ProductDeleteDto productDeleteDto) {
        this.productDao.deleteById(productDeleteDto.getId());
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
