package com.ecommerce.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

public interface AuthenticationValidator {

    String validateAndGetName(Authentication authentication) throws AuthenticationException;

    void validateUser(String user1Email, String user2Email) throws AuthenticationException;
}
