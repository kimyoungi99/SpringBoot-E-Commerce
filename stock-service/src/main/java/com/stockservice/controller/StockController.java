package com.stockservice.controller;

import com.stockservice.dto.ResponseDto;
import com.stockservice.dto.StockCheckDto;
import com.stockservice.dto.StockCheckResultDto;
import com.stockservice.dto.StockUpdateDto;
import com.stockservice.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/stock-service")
public class StockController {

    private final StockService stockService;

    @PostMapping (value = "/check")
    public ResponseEntity<ResponseDto> check(StockCheckDto stockCheckDto) {
        StockCheckResultDto stockCheckResultDto = this.stockService.checkStock(stockCheckDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(stockCheckResultDto)
                        .build()
                );
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ResponseDto> update(StockUpdateDto stockUpdateDto) {
        this.stockService.updateStock(stockUpdateDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(null)
                        .build()
                );
    }

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
}
