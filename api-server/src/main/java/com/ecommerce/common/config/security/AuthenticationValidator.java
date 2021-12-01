package com.ecommerce.common.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

public interface AuthenticationValidator {

    String validateAndGetName(Authentication authentication) throws AuthenticationException;
}
