package com.domohai.floral.application.usecase;

import com.domohai.floral.application.ports.out.AuthenticationService;
import com.domohai.floral.application.ports.out.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {
    /**
     * Trade-off: This approach does not follow Clean Architecture principles,
     * but we will use this approach since this is a simple project.
     *
     * If we were to follow Clean Architecture principles,
     * we would have to create a new class for the authentication manager
     * and inject it into the use case.
     */
    //private final AuthenticationManager authenticationManager;
    //private final JWTService jwtService;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    
    /**
     * Authenticates the user and returns a JWT on success.
     */
    public String login(String email, String rawPassword) {
        // Old implementation using AuthenticationManager
        //Authentication auth = authenticationManager.authenticate(
        //        new UsernamePasswordAuthenticationToken(email, rawPassword));
        
        // New implementation using AuthenticationService
        Authentication auth = authenticationService.authenticate(email, rawPassword);
        // Pass the entire Authentication to include roles and other claims
        return tokenService.generateToken(auth);
    }
    
    /**
     * The below code represents an alternative implementation of the
     * login method, which authenticates a user and generates a JWT
     * token upon successful authentication. This implementation follows
     * a more manual approach compared to the currently active method
     * in the class.
     *
     * First, the method attempts to load a User object from the
     * userRepository using the provided email. If no user is found,
     * it throws an InvalidCredentialsException.
     */
//    public String login(String email, String rawPassword) {
//        // Load user or throw if not found
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(InvalidCredentialsException::new);
//
//        // Verify password
//        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
//            throw new InvalidCredentialsException();
//        }
//
//        // Issue JWT
//        return jwtService.generateToken(user);
//    }
}
