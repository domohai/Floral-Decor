package com.domohai.floral.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    
    /**
     * Factory method to register a new user (roles must be provided).
     *
     * @param name      user's full name
     * @param email     user's email
     * @param password  hashed password
     * @param roles     non-empty set of roles
     * @return new User instance without id
     */
    public static User register(String name,
                                String email,
                                String password,
                                Set<Role> roles) {
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(email, "email cannot be null");
        Objects.requireNonNull(password, "password cannot be null");
        Objects.requireNonNull(roles, "roles cannot be null");
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .roles(roles)
                .build();
    }
    
    /**
     * Assigns an ID to a newly registered user after persistence.
     *
     * @param id generated database ID
     * @return new User instance with the given id
     */
    public User withId(Integer id) {
        return User.builder()
                .id(id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .roles(this.roles)
                .build();
    }
}
