package com.domohai.floral.infrastructure.security.adapter;

import com.domohai.floral.application.ports.out.TokenService;
import com.domohai.floral.infrastructure.security.jwt.JWTService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenServiceAdapter implements TokenService {
    private final JWTService jwtService;
    
    public JwtTokenServiceAdapter(JWTService jwtService) {
        this.jwtService = jwtService;
    }
    
    @Override
    public String generateToken(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
