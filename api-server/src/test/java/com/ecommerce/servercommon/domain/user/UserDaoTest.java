package com.ecommerce.servercommon.domain.user;

import com.ecommerce.servercommon.domain.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;
    private User user1Duplicate;

    @BeforeEach
    public void before() {
        this.user1 = new User(null, "kim", "young ki", "kimyoungi99@naver.com", "asdf", Role.USER, "asdf", 10000L);
        this.user2 = new User(null, "kim", "asdf", "yk0318ha@gmail.com", "asdf", Role.USER, "asdf", 20000L);
        this.user1Duplicate = new User(null, "kim", "young ki", "kimyoungi99@naver.com", "asdf", Role.USER, "asdf", 10000L);
    }

    @AfterEach
    public void rollback() {
        userDao.deleteById(this.user1.getId());
        userDao.deleteById(this.user2.getId());
        userDao.deleteById(this.user1Duplicate.getId());
    }

    @Test
    public void addAndFind() {
        this.userDao.add(this.user1);

        checkSameUser(this.user1, this.userDao.findById(this.user1.getId()));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
    }
}
