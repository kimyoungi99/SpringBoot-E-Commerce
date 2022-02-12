package com.categoryserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KafkaMessageDto {
    private String domain;

    private String eventType;

    private Object data;

    @Builder
    public KafkaMessageDto(String domain, String eventType, Object data) {
        this.domain = domain;
        this.eventType = eventType;
        this.data = data;
    }

    public KafkaMessageDto() {
    }
}
