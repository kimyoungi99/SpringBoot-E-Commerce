package com.ecommerce.servercommon.domain.order;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.enums.Role;
import com.ecommerce.servercommon.domain.product.Product;
import com.ecommerce.servercommon.domain.product.ProductDao;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
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
    @Autowired
    private UserDao userDao;

    private Product product1;
    private Product product2;
    private Order order1;
    private Order order2;
    private User user1;

    @BeforeEach
    public void before() {
        this.user1 = new User(null, "kim", "young ki", "kimyoungi99@naver.com", "asdf", Role.USER, "asdf", 10000L);
        this.userDao.add(this.user1);
        this.product1 = new Product(null, this.user1.getId(), "keyboard", 10000L);
        this.product2 = new Product(null, this.user1.getId(), "mouse", 20000L);
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
        userDao.deleteById(this.user1.getId());
    }

    @Test
    public void addAndFind() {
        this.order1 = new Order(null, this.product1.getId(), this.user1.getId(), LocalDateTime.now(), "Seoul",3, OrderStatus.PAYED);
        this.orderDao.add(this.order1);

        checkSameOrder(this.order1, this.orderDao.findById(this.order1.getId()));
    }

    @Test
    public void update() {
        this.order1 = new Order(null, this.product1.getId(), this.user1.getId(), LocalDateTime.now(), "Seoul",3, OrderStatus.PAYED);
        this.orderDao.add(this.order1);
        this.order1.setOrderStatus(OrderStatus.CANECLED);
        this.order1.setAddress("asdf");
        this.orderDao.update(this.order1);

        checkSameOrder(this.order1, this.orderDao.findById(this.order1.getId()));
    }

    private void checkSameOrder(Order order1, Order order2) {
        assertThat(order1.getId()).isEqualTo(order2.getId());
        assertThat(order1.getOrderStatus()).isEqualTo(order2.getOrderStatus());
        assertThat(order1.getAddress()).isEqualTo(order2.getAddress());
        assertThat(order1.getQuantity()).isEqualTo(order2.getQuantity());
    }

}
