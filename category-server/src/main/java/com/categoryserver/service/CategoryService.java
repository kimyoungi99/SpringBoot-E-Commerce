package com.categoryserver.service;

import com.categoryserver.dao.CategoryDao;
import com.categoryserver.domain.CategoryEntity;
import com.categoryserver.dto.CategoryAddDto;
import com.categoryserver.dto.CategoryResponseDto;
import com.categoryserver.dto.CategoryUpdateDto;
import com.categoryserver.dto.KafkaMessageDto;
import com.categoryserver.exception.CategoryNotExistingException;
import com.categoryserver.exception.KafkaConnectionException;
import com.categoryserver.mapper.CategoryUpdateDtoToCategoryEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;
    private final CategoryDao categoryDao;
    private final String categoryUpdateTopicName;

    public List<CategoryResponseDto> getAll() {
        return this.categoryDao.findAll().stream().map(CategoryEntity::toResponseDto).collect(Collectors.toList());
    }

    public void insert(CategoryAddDto categoryAddDto) {
        // init?
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(categoryAddDto.getName())
                .count(0L)
                .build();

        this.categoryDao.insert(categoryEntity);
    }

    public CategoryResponseDto info(String id) {
        Optional<CategoryEntity> optionalCategoryEntity = this.categoryDao.findById(id);

        CategoryEntity categoryEntity =
                optionalCategoryEntity.orElseThrow(
                        () -> new CategoryNotExistingException("존재하지 않는 카태고리 오류.")
                );

        return categoryEntity.toResponseDto();
    }

    @Transactional
    public void update(CategoryUpdateDto categoryUpdateDto) {
        Optional<CategoryEntity> optionalCategoryEntity = this.categoryDao.findById(categoryUpdateDto.getId());

        CategoryEntity categoryEntity =
                optionalCategoryEntity.orElseThrow(
                        () -> new CategoryNotExistingException("존재하지 않는 카태고리 오류.")
                );

        this.categoryDao.update(CategoryUpdateDtoToCategoryEntityMapper.map(categoryUpdateDto));

        // 카테고리 수정 시 전파
        if(!categoryEntity.getName().equals(categoryUpdateDto.getName())) {
            KafkaMessageDto kafkaMessageDto = KafkaMessageDto.builder()
                    .domain("CategoryService")
                    .eventType("CategoryUpdateEvent")
                    .data(CategoryUpdateDto.builder()
                            .id(categoryUpdateDto.getId())
                            .name(categoryUpdateDto.getName())
                            .build()
                    )
                    .build();
            try {
                this.kafkaTemplate.send(this.categoryUpdateTopicName, kafkaMessageDto);
            }
            catch (Exception e) {
                log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
                throw new KafkaConnectionException("카프카 응답 오류.");
            }
        }
    }

    public void addToCount(String id, Long value) {
        this.categoryDao.addToCount(id, value);
    }
}
