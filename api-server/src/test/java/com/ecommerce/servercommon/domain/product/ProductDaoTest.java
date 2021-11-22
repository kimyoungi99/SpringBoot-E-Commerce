package com.ecommerce.servercommon.domain.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        this.product1 = new Product(null, "키보드", 10000L);
        this.product2 = new Product(null, "마우스", 20000L);
    }

    @AfterEach
    public void rollback() {
        productDao.deleteById(this.product1.getId());
        productDao.deleteById(this.product2.getId());
    }

    @Test
    public void addAndFindById() {
        productDao.add(this.product1);
        productDao.add(this.product2);

        checkSameProduct(this.product1, productDao.findById(this.product1.getId()));
        checkSameProduct(this.product2, productDao.findById(this.product2.getId()));
    }

    @Test
    public void DeleteById() {
        productDao.add(this.product1);
        checkSameProduct(this.product1, productDao.findById(this.product1.getId()));

        productDao.deleteById(this.product1.getId());
        System.out.println(productDao.findById(this.product1.getId()));
        assertThat(productDao.findById(this.product1.getId())).isEqualTo(null);
    }

    @Test
    public void update() {
        productDao.add(this.product1);

        this.product1 = new Product(this.product1.getId(), "비싼 키보드", 1000000L);
        productDao.update(this.product1);

        checkSameProduct(productDao.findById(product1.getId()), this.product1);
    }

    private void checkSameProduct(Product product1, Product product2) {
        assertThat(product1.getId()).isEqualTo(product2.getId());
        assertThat(product1.getName()).isEqualTo(product2.getName());
        assertThat(product1.getPrice()).isEqualTo(product2.getPrice());
    }
}
