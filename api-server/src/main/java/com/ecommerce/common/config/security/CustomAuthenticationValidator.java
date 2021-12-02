package com.ecommerce.common.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
public class CustomAuthenticationValidator implements AuthenticationValidator{

    @Override
    public String validateAndGetName(Authentication authentication) throws AuthenticationException {

        if(authentication == null || this.isAnonymousUser(authentication))
            throw new AuthenticationException("권한 오류");

        return authentication.getName();
    }

    public boolean isAnonymousUser(Authentication authentication) {
        if(authentication.getName() == "AnonymousUser")
            return true;
        return false;
    }
}
