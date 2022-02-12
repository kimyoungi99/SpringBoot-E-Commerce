package com.stockservice.dao;

import com.stockservice.domain.StockEntity;

import java.sql.Connection;
import java.util.Optional;

public interface StockDao {

    void insert(StockEntity stockEntity);

    void updateStockWithLock(String productId, Long quantity);

    void updateStock(String productId, Long quantity);

    boolean checkWithLock(String productId, Long quantity);

    Optional<Long> findStockByProductId(String productId);

    void deleteByProductId(String productId);
}
