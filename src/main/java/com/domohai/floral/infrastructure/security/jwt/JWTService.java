package com.domohai.floral.infrastructure.security.jwt;

import com.domohai.floral.infrastructure.security.user.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JWTService {
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    
    @Value("${auth.jwt.secret}")
    private String secretKey;
    @Value("${auth.jwt.expiration}")
    private long validityInMilliseconds;
    
    /**
     * Generates a JWT token for the authenticated user.
     *
     * This method creates a JWT token containing the user's username, ID, and roles
     * as claims. The token is signed using a secret key and includes an expiration time.
     *
     * @param authentication The authentication object containing the user's details.
     * @return A signed JWT token as a String.
     */
    public String generateToken(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("id", user.getUser().getId())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + validityInMilliseconds))
                .signWith(key()).compact();
    }
    
    /**
     * Extracts the username from a given JWT token.
     *
     * This method parses the provided JWT token to retrieve the subject,
     * which represents the username of the authenticated user.
     *
     * @param token The JWT token from which the username is to be extracted.
     * @return The username contained in the JWT token.
     */
    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
            .verifyWith(key())
            .build()
            .parseSignedClaims(token)
            .getPayload().getSubject();
    }
    
    /**
     * Validates a given JWT token.
     *
     * This method checks the validity of the provided JWT token by parsing it
     * and verifying its signature using the secret key. If the token is valid,
     * the method returns true. Otherwise, it logs the specific error and returns false.
     *
     * @param token The JWT token to be validated.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
