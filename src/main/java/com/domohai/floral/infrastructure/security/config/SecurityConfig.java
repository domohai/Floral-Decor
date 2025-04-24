package com.domohai.floral.infrastructure.security.config;

import com.domohai.floral.infrastructure.security.jwt.AuthEntryPointJwt;
import com.domohai.floral.infrastructure.security.jwt.JWTFilter;
import com.domohai.floral.infrastructure.security.user.CustomUserDetailsService;
import com.domohai.floral.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
/*
  Enables method-level security in the application.
  The `@EnableGlobalMethodSecurity` annotation allows the use of annotations like `@PreAuthorize`
  and `@PostAuthorize` to secure methods based on roles, permissions, or other conditions.
 
  - `prePostEnabled = true`: Activates pre- and post-authorization annotations.
    For example:
    - `@PreAuthorize("hasRole('ADMIN')")` ensures a method can only be accessed by users with the ADMIN role.
    - `@PostAuthorize("returnObject.owner == authentication.name")` applies security checks after method execution.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;
    private final AuthEntryPointJwt unauthorizedHandler;
    
    /**
     * Configures the security filter chain for the application.
     *
     * This method defines the security settings for HTTP requests, including disabling CSRF protection,
     * handling unauthorized access, registering a custom authentication provider, and setting session
     * management to stateless. It also specifies which endpoints are publicly accessible and ensures
     * that all other requests require authentication. Additionally, it integrates a JWT filter into
     * the security filter chain to handle token-based authentication.
     *
     * @param http The `HttpSecurity` object used to configure security settings for HTTP requests.
     * @return A `SecurityFilterChain` object that defines the security configuration.
     * @throws Exception If an error occurs while building the security filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection to allow stateless authentication
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Handle unauthorized access
                .authenticationProvider(authenticationProvider()) // Register custom authentication provider
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless session management
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/**").permitAll() // Allow public access to authentication endpoints
                        .anyRequest().authenticated()); // Require authentication for all other requests
        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//                .authorizeHttpRequests(request -> request
//                        .anyRequest()
//                        .permitAll()
//
//                )
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(
//                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
    
    /**
     * Defines a bean for the `AuthenticationManager` to be used in the authentication process.
     *
     * The `AuthenticationManager` is a core component of Spring Security that is responsible
     * for processing authentication requests. It delegates the authentication process to the
     * configured `AuthenticationProvider`.
     *
     * @param config The `AuthenticationConfiguration` object that provides access to the
     *               `AuthenticationManager` instance configured in the application context.
     * @return An instance of `AuthenticationManager` that is used to authenticate users.
     * @throws Exception If there is an issue creating the `AuthenticationManager` instance.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Defines a bean for the `AuthenticationProvider` to be used in the authentication process.
     *
     * The `AuthenticationProvider` is responsible for retrieving user details and validating
     * credentials during authentication. This implementation uses the `DaoAuthenticationProvider`,
     * which interacts with a `UserDetailsService` to load user-specific data.
     *
     * @return An instance of `AuthenticationProvider` configured with the custom `UserDetailsService`
     *         and a `PasswordEncoder` for validating user credentials.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Set the custom user details service
        provider.setPasswordEncoder(passwordEncoder()); // Set the password encoder for credential validation
        return provider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(Const.BCRYPT_STRENGTH);
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:9002")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
