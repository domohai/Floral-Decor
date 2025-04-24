package com.domohai.floral.domain.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String roleName) {
        super("Role '" + roleName + "' not found.");
    }
}
