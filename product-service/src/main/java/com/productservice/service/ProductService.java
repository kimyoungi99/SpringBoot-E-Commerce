package com.productservice.service;

import com.productservice.dao.ProductDao;
import com.productservice.domain.ProductEntity;
import com.productservice.dto.ProductAddDto;
import com.productservice.dto.ProductDeleteDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.exception.ProductNotExistingException;
import com.productservice.mapper.ProductAddDtoToProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public void insert(ProductAddDto productAddDto) {
        ProductEntity productEntity = ProductAddDtoToProductEntityMapper.map(productAddDto);

        // init?
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
                        () -> new ProductNotExistingException("상품이 부존재 오류.")
                );

        return productEntity.toResponseDto();
    }
}
