package com.domohai.floral.infrastructure.security.jwt;

import com.domohai.floral.infrastructure.security.user.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;
    
    /**
     * Filter responsible for JWT-based authentication in HTTP requests.
     *
     * This filter intercepts incoming requests, extracts and validates JWT tokens
     * from the Authorization header, and sets up the security context for authenticated users.
     * It extends OncePerRequestFilter to ensure the filter is only executed once per request.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtService.validateToken(jwt)) {
                authenticateUser(request, jwt);
            }
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e);
        } catch (UsernameNotFoundException e) {
            logger.error("User not found: {}", e);
        } catch (Exception e) {
            logger.error("Authentication failed: {}", e);
        }
        filterChain.doFilter(request, response);
    }
    
    /**
     * Authenticates a user based on the provided JWT token.
     *
     * @param request The HTTP request
     * @param jwt The JWT token
     */
    private void authenticateUser(HttpServletRequest request, String jwt) {
        String username = jwtService.getUsernameFromJwt(jwt);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
    
    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
