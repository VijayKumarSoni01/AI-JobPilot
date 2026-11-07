
package com.aijobpilot.backend.service;

import com.aijobpilot.backend.dto.request.SignupRequest;
import com.aijobpilot.backend.dto.response.AuthResponse;
import com.aijobpilot.backend.entity.RefreshToken;
import com.aijobpilot.backend.entity.User;
import com.aijobpilot.backend.exception.InvalidRefreshTokenException;
import com.aijobpilot.backend.repository.RefreshTokenRepository;
import com.aijobpilot.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final List<String> testEmails = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        for (String email : testEmails) {
            userRepository.findByEmail(email).ifPresent(user -> {
                List<RefreshToken> tokens =
                        refreshTokenRepository.findAllByUser_Id(user.getId());

                refreshTokenRepository.deleteAll(tokens);
                userRepository.delete(user);
            });
        }
    }

    // 1. Verify refresh token rotation is persisted in MySQL

    @Test
    void refreshShouldRevokeOldTokenAndPersistNewToken() {

        IssuedToken issued = createTestUser();

        String oldHash = hash(issued.refreshToken());

        RefreshToken oldToken = refreshTokenRepository
                .findByTokenHash(oldHash)
                .orElseThrow();

        AuthResponse response = authService.refresh(issued.refreshToken());

        assertNotNull(response.getToken());
        assertNotNull(response.getRefreshToken());

        RefreshToken updatedOldToken = refreshTokenRepository
                .findByTokenHash(oldHash)
                .orElseThrow();

        assertNotNull(updatedOldToken.getRevokedAt());

        RefreshToken newToken = refreshTokenRepository
                .findByTokenHash(hash(response.getRefreshToken()))
                .orElseThrow();

        assertNull(newToken.getRevokedAt());
        assertEquals(oldToken.getUser().getId(), newToken.getUser().getId());
    }

    // 2. Verify old refresh token cannot be reused

    @Test
    void oldRefreshTokenShouldBeRejectedAfterRotation() {

        IssuedToken issued = createTestUser();

        AuthResponse response = authService.refresh(issued.refreshToken());

        assertNotNull(response.getRefreshToken());

        assertThrows(
                InvalidRefreshTokenException.class,
                () -> authService.refresh(issued.refreshToken())
        );
    }

    // 3. Verify logout revocation is persisted in MySQL

    @Test
    void logoutShouldPreventSubsequentRefresh() {

        IssuedToken issued = createTestUser();

        authService.logout(issued.refreshToken());

        RefreshToken storedToken = refreshTokenRepository
                .findByTokenHash(hash(issued.refreshToken()))
                .orElseThrow();

        assertNotNull(storedToken.getRevokedAt());

        assertThrows(
                InvalidRefreshTokenException.class,
                () -> authService.refresh(issued.refreshToken())
        );
    }

    // 4. Verify concurrent refresh requests allow only one rotation

    @Test
    void concurrentRefreshRequestsShouldAllowOnlyOneSuccess()
            throws Exception {

        IssuedToken issued = createTestUser();

        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            var refreshTask = (java.util.concurrent.Callable<String>) () -> {
                ready.countDown();

                if (!start.await(10, TimeUnit.SECONDS)) {
                    throw new IllegalStateException(
                            "Timed out waiting to start concurrent refresh"
                    );
                }

                try {
                    authService.refresh(issued.refreshToken());
                    return "SUCCESS";
                } catch (InvalidRefreshTokenException ex) {
                    return "REJECTED";
                }
            };

            Future<String> first = executor.submit(refreshTask);
            Future<String> second = executor.submit(refreshTask);

            assertTrue(
                    ready.await(10, TimeUnit.SECONDS),
                    "Both refresh requests should be ready"
            );

            start.countDown();

            String firstResult = first.get(30, TimeUnit.SECONDS);
            String secondResult = second.get(30, TimeUnit.SECONDS);

            List<String> results = List.of(firstResult, secondResult);

            assertEquals(
                    1,
                    results.stream().filter("SUCCESS"::equals).count(),
                    "Exactly one refresh request should succeed"
            );

            assertEquals(
                    1,
                    results.stream().filter("REJECTED"::equals).count(),
                    "The other refresh request should be rejected"
            );

            User user = userRepository.findByEmail(issued.email())
                    .orElseThrow();

            List<RefreshToken> tokens =
                    refreshTokenRepository.findAllByUser_Id(user.getId());

            assertEquals(
                    2,
                    tokens.size(),
                    "Only the original token and one replacement should exist"
            );

            RefreshToken original = refreshTokenRepository
                    .findByTokenHash(hash(issued.refreshToken()))
                    .orElseThrow();

            assertNotNull(original.getRevokedAt());

        } finally {
            start.countDown();
            executor.shutdownNow();
        }
    }

    private IssuedToken createTestUser() {

        String email = "refresh-test-" + UUID.randomUUID()
                + "@example.com";

        testEmails.add(email);

        SignupRequest request = new SignupRequest();
        request.setEmail(email);
        request.setPassword("TestPassword123!");

        AuthResponse response = authService.signup(request);

        return new IssuedToken(email, response.getRefreshToken());
    }

    private String hash(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(digest);

        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 is unavailable", ex);
        }
    }

    private record IssuedToken(String email, String refreshToken) {
    }
}
