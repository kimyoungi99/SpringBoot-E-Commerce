package com.ecommerce.service;

import com.ecommerce.common.security.AuthenticationValidator;
import com.ecommerce.servercommon.domain.product.Product;
import com.ecommerce.servercommon.domain.product.ProductDao;
import com.ecommerce.servercommon.domain.product.ProductDetails;
import com.ecommerce.servercommon.domain.product.ProductDetailsDao;
import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.ProductAddDto;
import com.ecommerce.servercommon.dto.ProductResponseDto;
import com.ecommerce.servercommon.dto.ProductUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AuthenticationValidator authenticationValidator;
    private final ProductDao productDao;
    private final ProductDetailsDao productDetailsDao;
    private final UserDao userDao;

    public List<Product> getAllProduct() {
        List<Product> result = this.productDao.findAll();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Transactional
    public Long addProduct(ProductAddDto productAddDto, String sellerEmail) {
        Product product = productAddDto.toProductEntity();
        product.setSellerId(userDao.findByEmail(sellerEmail).getId());
        this.productDao.add(product);

        ProductDetails pd = ProductDetails.builder()
                .productId(product.getId())
                .rating(new BigDecimal("0.0"))
                .reviewCount(0L)
                .sellCount(0L)
                .stock(productAddDto.getStock())
                .build();

        this.productDetailsDao.add(pd);

        return pd.getId();
    }

    public void updateProduct(ProductUpdateDto productUpdateDto, String userEmail) {
        Product product = productUpdateDto.toProductEntity();

        // check userId ==  sellerId
        this.authenticationValidator.validateUser(userEmail, this.productDao.findSellerById(product.getId()).getEmail());

        product.setSellerId(this.userDao.findByEmail(userEmail).getId());
        this.productDao.update(product);
    }

    public void deleteProduct(Long id, String userEmail) {
        this.authenticationValidator.validateUser(userEmail, this.productDao.findSellerById(id).getEmail());

        this.productDao.deleteById(id);
    }
}