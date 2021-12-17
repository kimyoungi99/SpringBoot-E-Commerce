package com.ecommerce.servercommon.domain.product;

import com.ecommerce.servercommon.domain.enums.Role;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductDetailsDaoTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDetailsDao productDetailsDao;

    private Product product1;
    private Product product2;
    private User user1;
    private ProductDetails productDetails1;

    @BeforeEach
    public void setUp() {
        this.user1 = new User(null, "kim", "young ki", "990318", "asdf", Role.USER, "asdf", 10000L);
        this.userDao.add(this.user1);
        this.product1 = new Product(null, this.user1.getId(), "keyboard", 10000L);
        this.product2 = new Product(null, this.user1.getId(), "mouse", 20000L);
        productDao.add(this.product1);
        productDao.add(this.product2);
        this.productDetails1 = new ProductDetails(null, this.product1.getId(), new BigDecimal("0.0"), 0L, 0L, 500L);
    }

    @AfterEach
    public void rollback() {
        this.productDetailsDao.deleteById(this.productDetails1.getId());
        this.productDao.deleteById(this.product1.getId());
        this.productDao.deleteById(this.product2.getId());
        this.userDao.deleteById(this.user1.getId());
    }

    @Test
    public void addAndFindById() {
        this.productDetailsDao.add(this.productDetails1);

        checkSameProductDetails(this.productDetails1, this.productDetailsDao.findById(this.productDetails1.getId()));
    }

    @Test
    public void increment() {
        this.productDetailsDao.add(this.productDetails1);
        this.productDetailsDao.incrementSellCountByProductId(this.productDetails1.getProductId());
        this.productDetailsDao.incrementReviewCountByProductId(this.productDetails1.getProductId());

        assertThat(this.productDetailsDao.findById(this.productDetails1.getId()).getReviewCount()).isEqualTo(this.productDetails1.getReviewCount() + 1);
        assertThat(this.productDetailsDao.findById(this.productDetails1.getId()).getSellCount()).isEqualTo(this.productDetails1.getSellCount() + 1);
    }

    private void checkSameProductDetails(ProductDetails product1, ProductDetails product2) {
        assertThat(product1.getId()).isEqualTo(product2.getId());
        assertThat(product1.getProductId()).isEqualTo(product2.getProductId());
        assertThat(product1.getRating()).isEqualTo(product2.getRating());
        assertThat(product1.getReviewCount()).isEqualTo(product2.getReviewCount());
        assertThat(product1.getSellCount()).isEqualTo(product2.getSellCount());

    }
}
