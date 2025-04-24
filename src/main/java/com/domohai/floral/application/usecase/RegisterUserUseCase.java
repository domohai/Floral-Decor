package com.domohai.floral.application.usecase;

import com.domohai.floral.application.ports.out.PasswordService;
import com.domohai.floral.domain.exception.RoleNotFoundException;
import com.domohai.floral.domain.exception.UserAlreadyExistsException;
import com.domohai.floral.domain.model.Role;
import com.domohai.floral.domain.model.User;
import com.domohai.floral.domain.repository.RoleRepository;
import com.domohai.floral.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    /**
     * Trade-off: do not follow Clean Architecture principles,
     * but we will use this approach since this is a simple project.
     *
     * If we were to follow Clean Architecture principles,
     * we would have to create an interface for the PasswordEncoder
     * and implement it in the infrastructure layer.
     */
    //private final PasswordEncoder passwordEncoder;
    
    /**
     * Registers a new user with the given details.
     *
     * @param name        user's full name
     * @param email       user's unique email
     * @param rawPassword user's raw password (will be hashed)
     * @return the persisted User with assigned ID
     * @throws UserAlreadyExistsException if a user with the email already exists
     * @throws RoleNotFoundException if default role is not found
     */
    public User register(String name, String email, String rawPassword) {
        // Ensure email is not taken
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new UserAlreadyExistsException(email);
        });
        
        // Load default ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("ROLE_USER"));
        System.out.println("User role: " + userRole);
        Set<Role> roles = Collections.singleton(userRole);
        
        // Hash password and create domain user
        String encodedPassword = passwordService.hashPassword(rawPassword);
        User newUser = User.register(name, email, encodedPassword, roles);
        
        // Persist and return
        return userRepository.save(newUser);
    }
}
