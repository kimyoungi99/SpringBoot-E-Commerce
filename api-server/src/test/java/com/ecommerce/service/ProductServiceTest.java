package com.ecommerce.service;

import com.ecommerce.servercommon.domain.enums.Role;
import com.ecommerce.servercommon.domain.product.Product;
import com.ecommerce.servercommon.domain.product.ProductDao;
import com.ecommerce.servercommon.domain.product.ProductDetails;
import com.ecommerce.servercommon.domain.product.ProductDetailsDao;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.ProductAddDto;
import com.ecommerce.servercommon.dto.ProductUpdateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.AuthenticationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductDetailsDao productDetailsDao;

    private ProductDetails productDetails1;
    private Product product1;
    private User user1;

    @BeforeEach
    public void setUp() {
        this.user1 = new User(null, "kim", "young ki", "990asdf318", "asdf", Role.USER, "asdf", 10000L);
        this.userDao.add(this.user1);
        this.product1 = new Product(null, this.user1.getId(), "keyboard", 10000L);
    }

    @AfterEach
    public void rollback() {
        this.productDetailsDao.deleteById(this.productDetails1.getId());
        this.productDao.deleteById(this.product1.getId());
        this.userDao.deleteById(this.user1.getId());
    }

    @Test
    public void addProductTest() {
        ProductAddDto productAddDto = new ProductAddDto("keyboard", 10000L, 500L);
        Long productDetailsId = this.productService.addProduct(productAddDto, this.user1.getEmail());
        this.productDetails1 = this.productDetailsDao.findById(productDetailsId);

        this.product1 = this.productDao.findById(this.productDetails1.getProductId());

        assertThat(productAddDto.getName()).isEqualTo(this.product1.getName());
        assertThat(productAddDto.getPrice()).isEqualTo(this.product1.getPrice());
    }

    @Test
    public void updateProductTest() throws AuthenticationException {
        this.productDao.add(this.product1);
        this.productDetails1 = new ProductDetails(null, this.product1.getId(), new BigDecimal("0.0"), 0L, 0L, 0L);
        this.productDetailsDao.add(this.productDetails1);
        ProductUpdateDto productUpdate = new ProductUpdateDto(this.product1.getId(), "keyboard", 20000L, 500L);

        productService.updateProduct(productUpdate, this.user1.getEmail());

        assertThat(productUpdate.getPrice()).isEqualTo(this.productDao.findById(this.product1.getId()).getPrice());
    }
}