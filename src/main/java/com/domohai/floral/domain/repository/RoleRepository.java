package com.domohai.floral.domain.repository;

import com.domohai.floral.domain.model.Role;

import java.util.Optional;

public interface RoleRepository {
    /**
     * Find a role by its name.
     *
     * @param name unique role name
     * @return Optional containing the Role if found, or empty otherwise
     */
    Optional<Role> findByName(String name);
}
