package com.ecommerce.common.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationValidator {

    String validateAndGetName(Authentication authentication);

    void validateUser(String user1Email, String user2Email);
}
