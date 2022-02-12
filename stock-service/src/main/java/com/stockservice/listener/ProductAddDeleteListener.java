package com.stockservice.listener;

import com.stockservice.dto.KafkaMessageDto;
import com.stockservice.dto.ProductAddDeleteMessageDto;
import com.stockservice.dto.StockAddDto;
import com.stockservice.exception.WrongEventTypeException;
import com.stockservice.mapper.MapToProductAddDeleteMessageDtoMapper;
import com.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductAddDeleteListener {

    private final StockService stockService;

    @KafkaListener(topics = "${topic.product.add-delete.name}", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void productAddDeleteListener(KafkaMessageDto kafkaMessageDto, Acknowledgment ack) {
        log.info("카프카 메세지 리스닝 성공 : " + kafkaMessageDto.getEventType());

        ProductAddDeleteMessageDto productAddDeleteMessageDto =
                MapToProductAddDeleteMessageDtoMapper.map((Map<String, Object>) kafkaMessageDto.getData());

        if (kafkaMessageDto.getEventType().equals("ProductAddEvent")) {
            this.stockService.addStock(StockAddDto.builder()
                    .productId(productAddDeleteMessageDto.getId())
                    .stock(productAddDeleteMessageDto.getStock())
                    .build()
            );
            log.info("수량 정보 저장 성공");
        } else if (kafkaMessageDto.getEventType().equals("ProductDeleteEvent")) {
            this.stockService.deleteByProductId(productAddDeleteMessageDto.getId());
            log.info("수량 정보 삭제 성공");
        } else {
            log.error("name: " + "WrongEventTypeException" + "\nmsg :" + "비정상적인 카프카 이벤트 종류 오류.");
            throw new WrongEventTypeException("비정상적인 카프카 이벤트 종류 오류.");
        }

        ack.acknowledge();
    }
}
