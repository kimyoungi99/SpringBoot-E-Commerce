package com.ecommerce.servercommon.domain.user;

import com.ecommerce.servercommon.domain.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;
    private User user1Duplicate;

    @BeforeEach
    public void before() {
        this.user1 = new User(null, "kim", "young ki", "akimyoungi99@naver.com", "asdf", Role.USER, "asdf", 10000L);
        this.user2 = new User(null, "kim", "asdf", "ayk0318ha@gmail.com", "asdf", Role.USER, "asdf", 20000L);
        this.user1Duplicate = new User(null, "kim", "young ki", "akimyoungi99@naver.com", "asdf", Role.USER, "asdf", 10000L);
    }

    @AfterEach
    public void rollback() {
        userDao.deleteById(this.user1.getId());
        userDao.deleteById(this.user2.getId());
        userDao.deleteById(this.user1Duplicate.getId());
    }

    @Test
    public void addAndFindById() {
        this.userDao.add(this.user1);
        this.userDao.add(this.user2);

        checkSameUser(this.user1, this.userDao.findById(this.user1.getId()));
        checkSameUser(this.user2, this.userDao.findById(this.user2.getId()));
    }

    @Test
    public void addAndFindByEmail() {
        this.userDao.add(this.user1);
        this.userDao.add(this.user2);

        checkSameUser(this.user1, this.userDao.findByEmail(this.user1.getEmail()));
        checkSameUser(this.user2, this.userDao.findByEmail(this.user2.getEmail()));
    }

    @Test
    public void duplicateEmail() {
        this.userDao.add(this.user1);
        assertThrows(DuplicateKeyException.class, ()-> {
            this.userDao.add(this.user1Duplicate);
        });
    }

    @Test
    public void update() {
        this.userDao.add(this.user1);
        this.user1.setPoint(10L);
        this.userDao.update(this.user1);

        checkSameUser(this.user1, this.userDao.findByEmail(this.user1.getEmail()));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getFirstName()).isEqualTo(user2.getFirstName());
        assertThat(user1.getLastName()).isEqualTo(user2.getLastName());
        assertThat(user1.getAddress()).isEqualTo(user2.getAddress());
        assertThat(user1.getRole()).isEqualTo(user2.getRole());
        assertThat(user1.getPoint()).isEqualTo(user2.getPoint());
    }
}
