package com.example.back.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.back.config.AppProps;
import com.example.back.entity.User;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 29 март 2023 г.
 */
@Component
@RequiredArgsConstructor
public class JWTTokenProvider {

    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);

    private final AppProps appProps;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + appProps.getExpirationTime());

        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("username", user.getUsername());
        claimsMap.put("firstname", user.getFirstname());
        claimsMap.put("lastname", user.getLastname());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProps.getSecret())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(appProps.getSecret())
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException |
                 MalformedJwtException |
                 ExpiredJwtException |
                 UnsupportedJwtException |
                 IllegalArgumentException ex) {
            LOG.error(ex.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProps.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong((String)claims.get("id"));
    }

}
