
package com.aijobpilot.authservice.service;

import com.aijobpilot.authservice.dto.request.LoginRequest;
import com.aijobpilot.authservice.dto.request.SignupRequest;
import com.aijobpilot.authservice.dto.response.AuthResponse;
import com.aijobpilot.authservice.entity.User;
import com.aijobpilot.authservice.exception.AccountInactiveException;
import com.aijobpilot.authservice.exception.DuplicateEmailException;
import com.aijobpilot.authservice.exception.InvalidCredentialsException;
import com.aijobpilot.authservice.entity.RefreshToken;
import com.aijobpilot.authservice.exception.InvalidRefreshTokenException;
import com.aijobpilot.authservice.repository.RefreshTokenRepository;
import com.aijobpilot.authservice.repository.UserRepository;
import com.aijobpilot.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email is already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .status("ACTIVE")
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        String refreshToken = issueRefreshToken(user);

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .role(user.getRole())
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new AccountInactiveException("Account is inactive or disabled");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        String refreshToken = issueRefreshToken(user);

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .role(user.getRole())
                .message("Login successful")
                .build();
    }

    @Transactional
    public AuthResponse refresh(String rawToken) {

        RefreshToken current = findUsableRefreshToken(rawToken);

        User user = current.getUser();

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new AccountInactiveException("Account is inactive or disabled");
        }

        current.setRevokedAt(LocalDateTime.now());
        refreshTokenRepository.save(current);

        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());

        return AuthResponse.builder()
                .token(jwtService.generateToken(details))
                .refreshToken(issueRefreshToken(user))
                .email(user.getEmail())
                .role(user.getRole())
                .message("Token refreshed successfully")
                .build();
    }

    @Transactional
    public void logout(String rawToken) {

        if (rawToken == null || rawToken.isBlank()) {
            return;
        }

        refreshTokenRepository.findByTokenHashForUpdate(hash(rawToken))
                .ifPresent(token -> {
                    if (token.getRevokedAt() == null) {
                        token.setRevokedAt(LocalDateTime.now());
                        refreshTokenRepository.save(token);
                    }
                });
    }

    private String issueRefreshToken(User user) {
        byte[] bytes = new byte[48];
        secureRandom.nextBytes(bytes);

        String raw = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        LocalDateTime now = LocalDateTime.now();

        refreshTokenRepository.save(RefreshToken.builder()
                .user(user)
                .tokenHash(hash(raw))
                .expiresAt(now.plusNanos(refreshTokenExpiration * 1_000_000))
                .createdAt(now)
                .build());

        return raw;
    }

    private RefreshToken findUsableRefreshToken(String rawToken) {

        if (rawToken == null || rawToken.isBlank()) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        RefreshToken token = refreshTokenRepository.findByTokenHashForUpdate(hash(rawToken))
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Invalid refresh token"));

        LocalDateTime now = LocalDateTime.now();

        if (token.getRevokedAt() != null || !token.getExpiresAt().isAfter(now)) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        return token;
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
}
