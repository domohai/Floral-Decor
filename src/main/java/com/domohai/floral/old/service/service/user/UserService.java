package com.domohai.floral.old.service.service.user;

import com.domohai.floral.old.controller.user.RegisterRequest;
import com.domohai.floral.old.enums.RoleType;
import com.domohai.floral.old.exception.ResourceNotFoundException;
import com.domohai.floral.old.model.Cart;
import com.domohai.floral.old.model.Role;
import com.domohai.floral.old.model.User;
import com.domohai.floral.old.repo.CartRepository;
import com.domohai.floral.old.repo.RoleRepository;
import com.domohai.floral.old.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    
    @Autowired
    public UserService(UserRepository userRepository,
                       CartRepository cartRepository,
                       PasswordEncoder encoder,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }
    
    @Override
    public User createUser(RegisterRequest registerRequest) {
        // Check if email already exists
        User user = userRepository.findByEmail(registerRequest.getEmail()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        String encryptedPassword = encoder.encode(registerRequest.getPassword());
        user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(encryptedPassword);
        // add role relationship
        Role role = roleRepository.findByRole(RoleType.ROLE_USER).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);
        return savedUser;
    }
    
    // The user can only update their name
    @Override
    public User updateUser(Integer id, String name) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        user.setName(name);
        return userRepository.save(user);
    }
    
    @Override
    public void deleteUser(Integer id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        });
    }
}
