package com.ecommerce.servercommon.domain.review;

import com.ecommerce.servercommon.domain.enums.OrderStatus;
import com.ecommerce.servercommon.domain.enums.Role;
import com.ecommerce.servercommon.domain.order.Order;
import com.ecommerce.servercommon.domain.order.OrderDao;
import com.ecommerce.servercommon.domain.product.Product;
import com.ecommerce.servercommon.domain.product.ProductDao;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewDaoTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReviewDao reviewDao;

    private Product product1;
    private Order order1;
    private User user1;
    private Review review1;

    @BeforeEach
    public void before() {
        this.user1 = new User(null, "kim", "young ki", "1q2w3e4r5t6y7u990123", "asdf", Role.USER, "asdf", 10000L);
        this.userDao.add(this.user1);
        this.product1 = new Product(null, this.user1.getId(), "keyboard", 10000L);
        this.productDao.add(this.product1);
        this.order1 = new Order(null, this.product1.getId(), this.user1.getId(), LocalDateTime.now(), "Seoul",3, OrderStatus.PAYED);
        this.orderDao.add(this.order1);
    }

    @AfterEach
    public void rollback() {
        reviewDao.deleteById(this.review1.getId());
        orderDao.deleteById(this.order1.getId());
        productDao.deleteById(this.product1.getId());
        userDao.deleteById(this.user1.getId());
    }

    @Test
    public void addAndFind() {
        this.review1 = new Review(null, this.order1.getId(), this.user1.getId(), new BigDecimal("3.5"), "만족스럽습니다.");
        this.reviewDao.add(this.review1);

        checkSameReview(this.review1, this.reviewDao.findById(this.review1.getId()));
    }

    private void checkSameReview(Review review1, Review review2) {
        assertThat(review1.getId()).isEqualTo(review2.getId());
        assertThat(review1.getReview()).isEqualTo(review2.getReview());
        assertThat(review1.getReviewerId()).isEqualTo(review2.getReviewerId());
        assertThat(review1.getOrderId()).isEqualTo(review2.getOrderId());
    }
}
