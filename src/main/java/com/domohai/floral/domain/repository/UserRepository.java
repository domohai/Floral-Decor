package com.domohai.floral.domain.repository;

import com.domohai.floral.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    /**
     * Find a user by their email.
     *
     * @param email unique user email
     * @return Optional containing the User if found, or empty if not
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Save a user (new or existing) to the repository.
     *
     * @param user user to persist
     * @return persisted user with an assigned id
     */
    User save(User user);
}
