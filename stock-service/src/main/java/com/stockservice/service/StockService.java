package com.stockservice.service;

import com.stockservice.dao.StockDao;
import com.stockservice.domain.StockEntity;
import com.stockservice.dto.*;
import com.stockservice.exception.KafkaConnectionException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockService {
    private final StockDao stockDao;
    private final KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;
    private final String stockUpdateTopicName;

    public void updateStock(StockUpdateDto stockUpdateDto) {
        this.stockDao.updateStock(stockUpdateDto.getProductId(), stockUpdateDto.getQuantity());

        KafkaMessageDto kafkaMessageDto = KafkaMessageDto.builder()
                .domain("StockService")
                .eventType("StockUpdateEvent")
                .data(stockUpdateDto)
                .build();

        try {
            this.kafkaTemplate.send(this.stockUpdateTopicName, kafkaMessageDto);
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new KafkaConnectionException("카프카 응답 오류.");
        }
    }

    public StockCheckResultDto checkStock(StockCheckDto stockCheckDto) {
        log.info("check 요청 : (productId : " + stockCheckDto.getProductId() + " quantity : " + stockCheckDto.getQuantity() + ")");
        boolean result = this.stockDao.checkWithLock(stockCheckDto.getProductId(), stockCheckDto.getQuantity());

        return StockCheckResultDto.builder()
                .result(result)
                .build();
    }

    public void addStock(StockAddDto stockAddDto) {
        this.stockDao.insert(StockEntity.builder()
                .productId(stockAddDto.getProductId())
                .stock(stockAddDto.getStock())
                .build()
        );
    }

    public void deleteByProductId(String productId) {
        this.stockDao.deleteByProductId(productId);
    }

    @Builder
    public StockService(StockDao stockDao, KafkaTemplate kafkaTemplate, String stockUpdateTopicName) {
        this.stockDao = stockDao;
        this.kafkaTemplate = kafkaTemplate;
        this.stockUpdateTopicName = stockUpdateTopicName;
    }
}
