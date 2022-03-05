package com.apigateway.controller;

import com.apigateway.dto.*;
import com.apigateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/join")
    public ResponseEntity<ResponseDto> join(@RequestBody UserJoinDto userJoinDto) {
        ResponseDto responseDto = this.userService.join(userJoinDto);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        ResponseDto responseDto = this.userService.login(userLoginDto);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ResponseDto> update(@RequestBody UserUpdateDto userUpdateDto) {
        ResponseDto responseDto = this.userService.update(userUpdateDto);

        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }
}
