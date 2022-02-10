package com.stockservice.service;

import com.stockservice.dao.StockDao;
import com.stockservice.dto.StockCheckDto;
import com.stockservice.dto.StockUpdateDto;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockDao stockDao;

    public void updateStock(StockUpdateDto stockUpdateDto) {
        this.stockDao.updateStock(stockUpdateDto.getProductId(), stockUpdateDto.getQuantity());
    }

    public void checkStock(StockCheckDto stockCheckDto) {
        boolean result = this.stockDao.checkWithLock(stockCheckDto.getProductId(), stockCheckDto.getQuantity());
    }

    public StockService(StockDao stockDao) {
        this.stockDao = stockDao;
    }
}
