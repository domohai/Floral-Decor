package com.domohai.floral.config;

import com.domohai.floral.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//    @Autowired
//    private MyUserDetailsService myUserDetailsService;
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        authProvider.setUserDetailsService(myUserDetailsService);
//        return authProvider;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
}
