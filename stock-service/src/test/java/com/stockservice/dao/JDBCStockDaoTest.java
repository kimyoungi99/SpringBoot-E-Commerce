package com.stockservice.dao;

import com.stockservice.domain.StockEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JDBCStockDaoTest {

    @Autowired
    StockDao stockDao;

    // test data
    StockEntity stockEntity;

    @BeforeEach
    public void init() {
        this.stockEntity = StockEntity.builder()
                .stock(100L)
                .productId("3")
                .build();
    }

    @AfterEach
    public void after() {
        this.stockDao.deleteByProductId(this.stockEntity.getProductId());
    }

    @Test
    @DisplayName("저장 & 상품 아이디로 조회 테스트")
    public void insertAndFindStockByProductIdTest() {
        this.stockDao.insert(this.stockEntity);
        Long stock = this.stockDao.findStockByProductId(this.stockEntity.getProductId()).get();

        assertThat(stock).isEqualTo(this.stockEntity.getStock());
    }

    @Test
    @DisplayName("체크 성공 테스트")
    public void checkWithLockTrueTest() {
        Long quantity = 10L;
        this.stockDao.insert(this.stockEntity);
        boolean result = this.stockDao.checkWithLock(this.stockEntity.getProductId(), quantity);

        Long stock = this.stockDao.findStockByProductId(this.stockEntity.getProductId()).get();

        assertThat(result).isEqualTo(true);
        assertThat(stock).isEqualTo(this.stockEntity.getStock() - quantity);
    }

    @Test
    @DisplayName("체크 실패 테스트")
    public void checkWithLockFalseTest() {
        Long quantity = 100000L;
        this.stockDao.insert(this.stockEntity);
        boolean result = this.stockDao.checkWithLock(this.stockEntity.getProductId(), quantity);

        Long stock = this.stockDao.findStockByProductId(this.stockEntity.getProductId()).get();

        assertThat(result).isEqualTo(false);
        assertThat(stock).isEqualTo(this.stockEntity.getStock());
    }

    @Test
    @DisplayName("다중 쓰레드 동시성 체크 테스트")
    public void checkWithLockMultiCoreTest() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        Long quantity = 30L;
        this.stockDao.insert(this.stockEntity);

        for (int i = 0; i < 5; i++) {
            service.execute(() -> {//
                boolean b = this.stockDao.checkWithLock(this.stockEntity.getProductId(), quantity);
            });
        }
        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        Long stock = this.stockDao.findStockByProductId(this.stockEntity.getProductId()).get();

        assertThat(stock).isEqualTo(10L);
    }

    @Test
    @DisplayName("수정 테스트")
    public void updateStockTest() {
        Long update = 10000L;
        this.stockDao.insert(this.stockEntity);
        this.stockDao.updateStock(this.stockEntity.getProductId(), update);
        Long stock = this.stockDao.findStockByProductId(this.stockEntity.getProductId()).get();

        assertThat(stock).isEqualTo(update + this.stockEntity.getStock());
        assertThat(stock).isNotEqualTo(this.stockEntity.getStock());
    }
}