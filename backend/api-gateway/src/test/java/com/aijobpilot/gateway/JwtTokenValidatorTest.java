package com.aijobpilot.gateway;

import com.aijobpilot.gateway.security.JwtTokenValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenValidatorTest {
    private static final String SECRET = "defaultSecretKeyForAIJobPilotBackendApplicationMustBeLongEnough123456";
    private final JwtTokenValidator validator = new JwtTokenValidator(SECRET);

    @Test void rejectsMissingToken() { assertThrows(IllegalArgumentException.class, () -> validator.validate("")); }
    @Test void rejectsInvalidToken() { assertThrows(Exception.class, () -> validator.validate("not.a.jwt")); }
    @Test void rejectsExpiredToken() {
        String token = Jwts.builder().subject("user@example.com").expiration(Date.from(Instant.now().minusSeconds(1)))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8))).compact();
        assertThrows(Exception.class, () -> validator.validate(token));
    }
    @Test void acceptsValidToken() {
        String token = Jwts.builder().subject("user@example.com").expiration(Date.from(Instant.now().plus(Duration.ofMinutes(5))))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8))).compact();
        assertEquals("user@example.com", validator.validate(token).getSubject());
    }
}
