package com.categoryserver.controller;

import com.categoryserver.dto.CategoryAddDto;
import com.categoryserver.dto.CategoryResponseDto;
import com.categoryserver.dto.CategoryUpdateDto;
import com.categoryserver.dto.ResponseDto;
import com.categoryserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category-service")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseDto> add(@RequestBody CategoryAddDto categoryAddDto) {
        log.info("Request : add");
        this.categoryService.insert(categoryAddDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .build()
                );
    }

    @GetMapping(value = "/all")
    public ResponseEntity<ResponseDto> all() {
        log.info("Request : all");
        List<CategoryResponseDto> categories = this.categoryService.getAll();

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(categories)
                        .build()
                );
    }

    @GetMapping(value = "/info/{id}")
    public ResponseEntity<ResponseDto> info(@PathVariable String id) {
        log.info("Request : info");
        CategoryResponseDto info = this.categoryService.info(id);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(info)
                        .build()
                );
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ResponseDto> update(@RequestBody CategoryUpdateDto categoryUpdateDto) {
        log.info("Request : update");
        this.categoryService.update(categoryUpdateDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .build());
    }
}
