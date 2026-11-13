package com.aijobpilot.authservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "defaultSecretKeyForAIJobPilotAuthServiceApplicationMustBeLongEnough123456");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);

        userDetails = new User("test@example.com", "password", Collections.emptyList());
    }

    @Test
    void testGenerateAndExtractToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);

        String username = jwtService.extractUsername(token);
        assertEquals("test@example.com", username);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void rejectsExpiredToken() throws InterruptedException {
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1L);
        String token = jwtService.generateToken(userDetails);
        Thread.sleep(10);

        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void rejectsTokenSignedWithDifferentSecret() {
        String token = jwtService.generateToken(userDetails);
        ReflectionTestUtils.setField(jwtService, "secretKey", "differentSecretKeyForAIJobPilotAuthServiceApplicationMustBeLongEnough123456");

        assertThrows(JwtException.class, () -> jwtService.extractUsername(token));
    }

    @Test
    void rejectsTokenForDifferentUser() {
        String token = jwtService.generateToken(userDetails);
        UserDetails differentUser = new User("other@example.com", "password", Collections.emptyList());

        assertFalse(jwtService.isTokenValid(token, differentUser));
    }
}
