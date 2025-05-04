package com.domohai.floral.infrastructure.controller;

import com.domohai.floral.application.usecase.LoginUserUseCase;
import com.domohai.floral.application.usecase.RegisterUserUseCase;
import com.domohai.floral.infrastructure.dto.ApiResponse;
import com.domohai.floral.infrastructure.dto.JwtResponse;
import com.domohai.floral.infrastructure.dto.LoginRequest;
import com.domohai.floral.infrastructure.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    
    /**
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request
    ) {
        registerUserUseCase.register(
            request.getName(),
            request.getEmail(),
            request.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    /**
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(
            @RequestBody LoginRequest request
    ) {
        String token = loginUserUseCase.login(
            request.getEmail(),
            request.getPassword()
        );
        JwtResponse tokenData = new JwtResponse(token);
        ApiResponse<JwtResponse> response = ApiResponse.success(tokenData);
        return ResponseEntity.ok(response);
    }
}
