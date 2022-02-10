package com.stockservice.service;

import com.stockservice.dao.JDBCStockDao;
import com.stockservice.dto.StockCheckDto;
import com.stockservice.dto.StockUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class StockServiceTest {

    @Test
    public void updateStockTest() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/stock");
        dataSource.setUsername("root");
        dataSource.setPassword("yk0318ha");

        StockService stockService = new StockService(new JDBCStockDao(dataSource));

        stockService.updateStock(StockUpdateDto.builder()
                .productId("3")
                .quantity(20L)
                .build()
        );
    }

    @Test
    public void checkStockTest() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/stock");
        dataSource.setUsername("root");
        dataSource.setPassword("yk0318ha");

        StockService stockService = new StockService(new JDBCStockDao(dataSource));

        stockService.checkStock(StockCheckDto.builder()
                .productId("3")
                .quantity(10L)
                .build()
        );
    }

    @Test
    public void executeTest() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/stock");
        dataSource.setUsername("root");
        dataSource.setPassword("yk0318ha");

        JDBCStockDao jdbcStockDao = new JDBCStockDao(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        for (int i = 0; i < 5; i++) {
            service.execute(() -> {//
                StockService stockService = new StockService(new JDBCStockDao(dataSource));

                stockService.checkStock(StockCheckDto.builder()
                        .productId("3")
                        .quantity(10L)
                        .build()
                );
            });
        }
        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}