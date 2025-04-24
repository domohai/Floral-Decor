package com.domohai.floral.application.ports.out;

public interface PasswordService {
    String hashPassword(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
