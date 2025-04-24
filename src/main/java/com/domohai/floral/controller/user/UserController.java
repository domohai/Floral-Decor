package com.domohai.floral.controller.user;

import com.domohai.floral.controller.ApiResponse;
import com.domohai.floral.dto.JWTResponse;
import com.domohai.floral.dto.UserDTO;
import com.domohai.floral.model.User;
import com.domohai.floral.infrastructure.security.jwt.JWTService;
import com.domohai.floral.infrastructure.security.user.CustomUserDetails;
import com.domohai.floral.service.user.IUserService;
import com.domohai.floral.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final IUserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public UserController(UserService userService,
                          JWTService jwtService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @RequestBody RegisterRequest user
    ) {
        User savedUser = userService.createUser(user);
        UserDTO userDTO = new UserDTO(savedUser);
        System.out.println("User registered: " + userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse( "User registered successfully", userDTO));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();
        return ResponseEntity.ok(new ApiResponse("User logged in successfully", new JWTResponse(jwt, userDetails.getUser().getId(), userDetails.getUsername(), userDetails.getUser().getName(), roles)));
    }
}
