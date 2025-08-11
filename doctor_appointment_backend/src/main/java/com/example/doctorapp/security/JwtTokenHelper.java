package com.example.doctorapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenHelper {

    @Value(value="${jwt.secret}")
    private String secret;

    @Value(value="${jwt.expiration}")
    private long expiration; 

    private Key key;

    @PostConstruct
    public void init() {
//        byte[] decodedKey = Base64.getDecoder().decode(secret);
//        this.key = Keys.hmacShaKeyFor(decodedKey);
        
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    // Generate Token
    public String generateToken(String userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(userDetails)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // Get username from token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // General method to extract any claim
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Token validation
    public boolean validateToken(String token, UserDetails userDetails) {
        String tokenUsername = getUsernameFromToken(token);
        return tokenUsername.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }
}