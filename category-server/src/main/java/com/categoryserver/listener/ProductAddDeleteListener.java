package com.categoryserver.listener;

import com.categoryserver.dto.KafkaMessageDto;
import com.categoryserver.dto.ProductAddDeleteMessageDto;
import com.categoryserver.exception.WrongEventTypeException;
import com.categoryserver.mapper.MapToProductAddDeleteMessageDtoMapper;
import com.categoryserver.service.CategoryService;
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

    private final CategoryService categoryService;

    @KafkaListener(topics = "${topic.product.add-delete.name}", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void productAddDeleteListener(KafkaMessageDto kafkaMessageDto, Acknowledgment ack) {
        log.info("카프카 메세지 리스닝 성공 : " + kafkaMessageDto.getEventType());

        ProductAddDeleteMessageDto productAddDeleteMessageDto =
                MapToProductAddDeleteMessageDtoMapper.map((Map<String, String>) kafkaMessageDto.getData());

        if(kafkaMessageDto.getEventType().equals("ProductAddEvent")) {
            this.categoryService.addToCount(productAddDeleteMessageDto.getCategoryId(), +1L);
        }
        else if(kafkaMessageDto.getEventType().equals("ProductDeleteEvent")) {
            this.categoryService.addToCount(productAddDeleteMessageDto.getCategoryId(), -1L);
        }
        else {
            log.error("name: " + "WrongEventTypeException" + "\nmsg :" + "비정상적인 카프카 이벤트 종류 오류.");
            throw new WrongEventTypeException("비정상적인 카프카 이벤트 종류 오류.");
        }
        log.info("카테고리 count 업데이트 성공");

        ack.acknowledge();
    }
}
