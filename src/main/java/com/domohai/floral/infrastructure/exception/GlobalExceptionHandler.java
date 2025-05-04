package com.domohai.floral.infrastructure.exception;

import com.domohai.floral.domain.exception.RoleNotFoundException;
import com.domohai.floral.domain.exception.UserAlreadyExistsException;
import com.domohai.floral.infrastructure.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {
        
        logger.warn("Registration failed: Email already exists - {}", ex.getMessage());
        
        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Email already in use: " + ex.getMessage(),
                "EMAIL_ALREADY_EXISTS"
        );
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleNotFoundException(
            RoleNotFoundException ex, WebRequest request) {
        
        logger.error("Server configuration error: Required role not found - {}", ex.getMessage());
        
        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Required role not found: " + ex.getMessage(),
                "ROLE_NOT_FOUND"
        );
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        
        logger.warn("Authentication failed: Invalid credentials for request to {}",
                request.getDescription(false));
        
        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid email or password",
                "INVALID_CREDENTIALS"
        );
        
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    
    // Catch-all for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllExceptions(
            Exception ex, WebRequest request
    ) {
        
        logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        
        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                "SERVER_ERROR"
        );
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
