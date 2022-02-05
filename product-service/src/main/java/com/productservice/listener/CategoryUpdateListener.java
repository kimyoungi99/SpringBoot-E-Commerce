package com.productservice.listener;

import com.productservice.dto.CategoryUpdateDto;
import com.productservice.dto.KafkaMessageDto;
import com.productservice.mapper.MapToCategoryUpdateDtoMapper;
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
public class CategoryUpdateListener {

    private final ProductService productService;

    @KafkaListener(topics = "${topic.category.update.name}", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void userUpdateListener(KafkaMessageDto kafkaMessageDto , Acknowledgment ack) {
        log.info("카프카 메세지 리스닝 성공 : " + kafkaMessageDto.getEventType());

        CategoryUpdateDto categoryUpdateDto = MapToCategoryUpdateDtoMapper.map((Map<String, String>)  kafkaMessageDto.getData());
        this.productService.updateCategoryName(categoryUpdateDto);

        log.info("카테고리 이름 업데이트 성공 : " + "(id : " + categoryUpdateDto.getId() + ", name : " + categoryUpdateDto.getName());

        ack.acknowledge();
    }
}
