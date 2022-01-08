package com.ecommerce.common.security;

import com.ecommerce.common.exception.AuthorityException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationValidator implements AuthenticationValidator{

    @Override
    public String validateAndGetName(Authentication authentication) {

        if(authentication == null || this.isAnonymousUser(authentication))
            throw new AuthorityException("로그인 오류");

        return authentication.getName();
    }

    @Override
    public void validateUser(String user1Email, String user2Email) {
        if(!user1Email.equals(user2Email))
            throw new AuthorityException("판매자 권한 오류");

        return;
    }

    public boolean isAnonymousUser(Authentication authentication) {
        if(authentication.getName().equals("AnonymousUser"))
            return true;
        return false;
    }
}
