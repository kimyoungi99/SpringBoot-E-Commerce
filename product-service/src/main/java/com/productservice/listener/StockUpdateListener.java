package com.productservice.listener;

import com.productservice.dto.CategoryUpdateDto;
import com.productservice.dto.KafkaMessageDto;
import com.productservice.dto.StockUpdateDto;
import com.productservice.mapper.MapToCategoryUpdateDtoMapper;
import com.productservice.mapper.MapToStockUpdateDtoMapper;
import com.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockUpdateListener {

    private final ProductService productService;

    @KafkaListener(topics = "${topic.stock.update.name}", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void stockUpdateListener(KafkaMessageDto kafkaMessageDto, Acknowledgment ack) {
        log.info("카프카 메세지 리스닝 성공 : " + kafkaMessageDto.getEventType());

        StockUpdateDto stockUpdateDto = MapToStockUpdateDtoMapper.map((Map<String, Object>)  kafkaMessageDto.getData());
        this.productService.updateStock(stockUpdateDto);

        log.info("상품 수량 업데이트 성공 : " + "(id : " + stockUpdateDto.getProductId() + ", name : " + stockUpdateDto.getQuantity());

        ack.acknowledge();
    }
}
