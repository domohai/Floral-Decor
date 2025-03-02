package com.domohai.floral.controller.user;

import com.domohai.floral.controller.ApiResponse;
import com.domohai.floral.dto.UserDTO;
import com.domohai.floral.model.User;
import com.domohai.floral.service.user.IUserService;
import com.domohai.floral.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final IUserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @RequestBody RegisterRequest user
    ) {
        User savedUser = userService.createUser(user);
        UserDTO userDTO = new UserDTO(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse( "User registered successfully", userDTO));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        // TODO: Implement login
        System.out.println("Login request: " + loginRequest.getEmail() + " " + loginRequest.getPassword());
        return ResponseEntity.ok(new ApiResponse("Login successful", null));
    }
}
