package com.ecommerce.restcontroller;

import com.ecommerce.common.response.HttpResponseDto;
import com.ecommerce.common.response.ResponseBuilder;
import com.ecommerce.servercommon.dto.UserJoinDto;
import com.ecommerce.servercommon.dto.UserLoginDto;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ResponseBuilder responseBuilder;

    // 회원가입
    @PostMapping(value = "/join")
    public ResponseEntity<HttpResponseDto> join(@RequestBody UserJoinDto userJoinDto) {
        userService.join(userJoinDto);

        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "회원 가입 성공",
                null
        );
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<HttpResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("Token", token);

        return responseBuilder.jsonResponseBuild(
                HttpStatus.OK,
                "로그인 성공",
                data
        );
    }
}
