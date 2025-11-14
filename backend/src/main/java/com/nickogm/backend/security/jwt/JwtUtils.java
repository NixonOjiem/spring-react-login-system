package com.nickogm.backend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.nickogm.backend.security.services.UserDetailsServiceImpl;
import com.nickogm.backend.security.services.UserDetailsImpl;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${nickogm.app.jwtSecret}")
    private String jwtSecret;

    @Value("${nickogm.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Assuming you have a UserDetails implementation for loading user info
    // Replace UserDetailsImpl with your actual UserDetails class if named
    // differently
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build() // <--- CRITICAL: Must call build() to get the final JwtParser object
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
