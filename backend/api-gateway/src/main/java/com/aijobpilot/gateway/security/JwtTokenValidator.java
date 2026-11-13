package com.aijobpilot.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenValidator {
    private final SecretKey signingKey;

    public JwtTokenValidator(@Value("${gateway.jwt.secret}") String secret) {
        if (secret == null || secret.length() < 32)
            throw new IllegalArgumentException("gateway.jwt.secret must be at least 32 characters");
        signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims validate(String token) throws JwtException {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
}
