package com.domohai.floral.infrastructure.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    
    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
