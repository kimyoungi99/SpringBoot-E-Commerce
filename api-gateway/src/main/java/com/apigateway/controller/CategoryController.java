package com.apigateway.controller;

import com.apigateway.dto.CategoryAddDto;
import com.apigateway.dto.CategoryUpdateDto;
import com.apigateway.dto.OrderAddDto;
import com.apigateway.dto.ResponseDto;
import com.apigateway.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/getAll")
    public ResponseEntity<ResponseDto> getOrderList() {

        ResponseDto responseDto = this.categoryService.getAll();

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ResponseDto> update(
            @RequestBody CategoryUpdateDto categoryUpdateDto
    ) {

        ResponseDto responseDto = this.categoryService.update(categoryUpdateDto);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseDto> add(
            @RequestBody CategoryAddDto categoryAddDto
    ) {

        ResponseDto responseDto = this.categoryService.add(categoryAddDto);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }
}
