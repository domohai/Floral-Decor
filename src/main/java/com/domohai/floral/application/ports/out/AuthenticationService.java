package com.domohai.floral.application.ports.out;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication authenticate(String username, String password);
}
