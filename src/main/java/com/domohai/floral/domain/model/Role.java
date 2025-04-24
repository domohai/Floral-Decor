package com.domohai.floral.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    private Integer id;
    private String name;
    
    public static Role of(String name) {
        Objects.requireNonNull(name, "role name cannot be null");
        return Role.builder()
                .name(name)
                .build();
    }
}
