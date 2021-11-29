package com.ecommerce.service;

import com.ecommerce.common.config.security.JwtTokenProvider;
import com.ecommerce.servercommon.domain.enums.Role;
import com.ecommerce.servercommon.domain.user.User;
import com.ecommerce.servercommon.domain.user.UserDao;
import com.ecommerce.servercommon.dto.UserJoinDto;
import com.ecommerce.servercommon.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void join(UserJoinDto userJoinDto) {
        User user = userJoinDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
    }

    public String login(UserLoginDto userLoginDto) {
        User user = userDao.findByEmail(userLoginDto.getEmail());
        if(user == null)
            throw new IllegalArgumentException("존재하지 않는 아이디/비밀번호 오류");
        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("존재하지 않는 아이디/비밀번호 오류");
        }

        // 권한 부여
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        if(user.getRole() == Role.SELLER) {
            roles.add("SELLER");
        }
        return jwtTokenProvider.createToken(user.getUsername(), roles);
    }
}
