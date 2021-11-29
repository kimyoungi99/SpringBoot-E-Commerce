package com.ecommerce.restcontroller;

import com.ecommerce.common.response.HttpResponseDto;
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

    // 회원가입
    @PostMapping(value = "/join")
    public ResponseEntity<HttpResponseDto> join(@RequestBody UserJoinDto userJoinDto) {
        userService.join(userJoinDto);

        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        body.setMessage("회원 가입 성공");
        body.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<HttpResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);

        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        body.setMessage("로그인 성공");
        body.setStatus(HttpStatus.OK);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("Token", token);
        body.setData(data);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
