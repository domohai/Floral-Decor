package com.domohai.floral.infrastructure.controller.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
