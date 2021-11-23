package com.ecommerce.servercommon.domain.order;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.product.Product;
import com.ecommerce.servercommon.domain.product.ProductDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderDaoTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;

    private Product product1;
    private Product product2;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void before() {
        this.product1 = new Product(null, "keyboard", 10000L);
        this.product2 = new Product(null, "mouse", 20000L);
        this.productDao.add(this.product1);
        this.productDao.add(this.product2);
    }

    @AfterEach
    public void rollback() {
        if(this.order1 != null)
            orderDao.deleteById(this.order1.getId());
        if(this.order2 != null)
            orderDao.deleteById(this.order2.getId());
        productDao.deleteById(this.product1.getId());
        productDao.deleteById(this.product2.getId());
    }

    @Test
    public void addAndFind() {
        this.order1 = new Order(null, this.product1.getId(), LocalDateTime.now(), "Seoul",3, OrderStatus.PAYED);
        this.orderDao.add(this.order1);

        checkSameOrder(this.order1, orderDao.findById(this.order1.getId()));
    }

    private void checkSameOrder(Order order1, Order order2) {
        assertThat(order1.getId()).isEqualTo(order2.getId());
        assertThat(order1.getOrderStatus()).isEqualTo(order2.getOrderStatus());
        assertThat(order1.getAddress()).isEqualTo(order2.getAddress());
        assertThat(order1.getQuantity()).isEqualTo(order2.getQuantity());
    }

}
