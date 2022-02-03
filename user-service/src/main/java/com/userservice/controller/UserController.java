package com.userservice.controller;

import com.userservice.dto.*;
import com.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/join")
    public ResponseEntity<ResponseDto> join(@RequestBody UserJoinDto userJoinDto) {
        this.userService.join(userJoinDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .build());
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<ResponseDto> delete(@RequestBody UserDeleteDto userDeleteDto) {
        this.userService.delete(userDeleteDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .build());
    }

    @GetMapping(value = "/info/{email}")
    public ResponseEntity<ResponseDto> info(@PathVariable String email) {
        UserResponseDto info = this.userService.info(email);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .data(info)
                        .build());
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ResponseDto> update(@RequestBody UserUpdateDto userUpdateDto) {
        this.userService.update(userUpdateDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("ok")
                        .dateTime(LocalDateTime.now())
                        .build());
    }
}
