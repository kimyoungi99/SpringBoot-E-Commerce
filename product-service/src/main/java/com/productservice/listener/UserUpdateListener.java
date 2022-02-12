package com.productservice.listener;

import com.productservice.dto.EmailUpdateDto;
import com.productservice.dto.KafkaMessageDto;
import com.productservice.mapper.MapToEmailUpdateDtoMapper;
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
public class UserUpdateListener {

    private final ProductService productService;

    @KafkaListener(topics = "${topic.user.update.name}", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void userUpdateListener(KafkaMessageDto kafkaMessageDto , Acknowledgment ack) {
        log.info("카프카 메세지 리스닝 성공 : " + kafkaMessageDto.getEventType());

        EmailUpdateDto emailUpdateDto = MapToEmailUpdateDtoMapper.map((Map<String, String>) kafkaMessageDto.getData());
        this.productService.updateSellerEmail(emailUpdateDto);

        log.info("판매자 이메일 업데이트 성공 : " + "(id : " + emailUpdateDto.getId() + ", email : " + emailUpdateDto.getEmail());

        ack.acknowledge();
    }
}
