package com.domohai.floral.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private int status;
    private T data;
    private ErrorDetails error;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDetails {
        private String message;
        private String code;
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .data(data)
                .build();
    }
    
    public static <T> ApiResponse<T> error(int status, String message, String code) {
        return ApiResponse.<T>builder()
                .status(status)
                .error(ErrorDetails.builder()
                        .message(message)
                        .code(code)
                        .build())
                .build();
    }
}
