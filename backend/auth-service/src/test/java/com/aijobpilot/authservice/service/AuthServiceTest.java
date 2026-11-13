
package com.aijobpilot.authservice.service;

import com.aijobpilot.authservice.dto.request.LoginRequest;
import com.aijobpilot.authservice.dto.request.SignupRequest;
import com.aijobpilot.authservice.dto.response.AuthResponse;
import com.aijobpilot.authservice.entity.RefreshToken;
import com.aijobpilot.authservice.entity.User;
import com.aijobpilot.authservice.exception.DuplicateEmailException;
import com.aijobpilot.authservice.exception.InvalidCredentialsException;
import com.aijobpilot.authservice.exception.InvalidRefreshTokenException;
import com.aijobpilot.authservice.repository.RefreshTokenRepository;
import com.aijobpilot.authservice.repository.UserRepository;
import com.aijobpilot.authservice.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail("new@example.com");
        signupRequest.setPassword("password123");

        ReflectionTestUtils.setField(
                authService,
                "refreshTokenExpiration",
                3600000L
        );
    }

    // 1. Signup success

    @Test
    void testSignupSuccess() {

        when(userRepository.existsByEmail("new@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        when(userDetailsService.loadUserByUsername("new@example.com"))
                .thenReturn(mock(UserDetails.class));

        when(jwtService.generateToken(any(UserDetails.class)))
                .thenReturn("mockJwtToken");

        AuthResponse response = authService.signup(signupRequest);

        assertNotNull(response);
        assertEquals("new@example.com", response.getEmail());
        assertEquals("mockJwtToken", response.getToken());
        assertNotNull(response.getRefreshToken());
        assertEquals("USER", response.getRole());
        assertEquals("User registered successfully", response.getMessage());

        verify(passwordEncoder).encode("password123");

        verify(userRepository).save(argThat(user ->
                user.getEmail().equals("new@example.com")
                        && user.getPasswordHash().equals("encodedPassword")
                        && user.getRole().equals("USER")
                        && user.getStatus().equals("ACTIVE")
        ));

        verify(jwtService).generateToken(any(UserDetails.class));
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    // 2. Signup duplicate email

    @Test
    void testSignupDuplicateEmail() {

        when(userRepository.existsByEmail("new@example.com"))
                .thenReturn(true);

        assertThrows(
                DuplicateEmailException.class,
                () -> authService.signup(signupRequest)
        );

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
        verify(jwtService, never()).generateToken(any());
        verifyNoInteractions(refreshTokenRepository);
    }

    // 3. Login success

    @Test
    void testLoginSuccess() {

        LoginRequest request = new LoginRequest();
        request.setEmail("new@example.com");
        request.setPassword("password123");

        User user = User.builder()
                .email("new@example.com")
                .role("USER")
                .status("ACTIVE")
                .build();

        UserDetails details = mock(UserDetails.class);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(userDetailsService.loadUserByUsername(request.getEmail()))
                .thenReturn(details);

        when(jwtService.generateToken(details))
                .thenReturn("access-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("access-token", response.getToken());
        assertNotNull(response.getRefreshToken());
        assertEquals("new@example.com", response.getEmail());
        assertEquals("USER", response.getRole());

        verify(authenticationManager).authenticate(any());
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    // 4. Login invalid email

    @Test
    void testLoginInvalidEmail() {

        LoginRequest request = new LoginRequest();
        request.setEmail("missing@example.com");
        request.setPassword("password123");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        assertThrows(
                InvalidCredentialsException.class,
                () -> authService.login(request)
        );

        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(jwtService);
        verifyNoInteractions(refreshTokenRepository);
    }

    // 5. Login incorrect password

    @Test
    void testLoginIncorrectPassword() {

        LoginRequest request = new LoginRequest();
        request.setEmail("new@example.com");
        request.setPassword("wrong-password");

        User user = User.builder()
                .email(request.getEmail())
                .role("USER")
                .status("ACTIVE")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        verify(jwtService, never()).generateToken(any());
        verify(refreshTokenRepository, never()).save(any());
    }

    // 6. Refresh token rotation and revocation

    @Test
    void testRefreshRotatesAndRevokesToken() {

        User user = User.builder()
                .id(1L)
                .email("new@example.com")
                .role("USER")
                .status("ACTIVE")
                .build();

        RefreshToken current = RefreshToken.builder()
                .user(user)
                .tokenHash("stored-hash")
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        UserDetails details = mock(UserDetails.class);

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.of(current));

        when(userDetailsService.loadUserByUsername(user.getEmail()))
                .thenReturn(details);

        when(jwtService.generateToken(details))
                .thenReturn("new-access-token");

        AuthResponse response = authService.refresh("old-refresh-token");

        assertNotNull(response);
        assertEquals("new-access-token", response.getToken());
        assertNotNull(response.getRefreshToken());
        assertEquals("new@example.com", response.getEmail());
        assertEquals("USER", response.getRole());
        assertEquals("Token refreshed successfully", response.getMessage());

        assertNotNull(current.getRevokedAt());

        verify(refreshTokenRepository)
                .findByTokenHashForUpdate(anyString());

        verify(refreshTokenRepository, atLeast(2))
                .save(any(RefreshToken.class));

        verify(jwtService).generateToken(details);
    }

    // 7. Revoked refresh token rejection

    @Test
    void testRevokedRefreshTokenIsRejected() {

        RefreshToken revoked = RefreshToken.builder()
                .user(User.builder()
                        .email("new@example.com")
                        .status("ACTIVE")
                        .build())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .revokedAt(LocalDateTime.now())
                .build();

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.of(revoked));

        assertThrows(
                InvalidRefreshTokenException.class,
                () -> authService.refresh("revoked-token")
        );

        verify(jwtService, never()).generateToken(any());
        verify(refreshTokenRepository, never()).save(any());
    }

    // 8. Expired refresh token rejection

    @Test
    void testExpiredRefreshTokenIsRejected() {

        RefreshToken expired = RefreshToken.builder()
                .user(User.builder()
                        .email("new@example.com")
                        .status("ACTIVE")
                        .build())
                .expiresAt(LocalDateTime.now().minusMinutes(5))
                .build();

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.of(expired));

        assertThrows(
                InvalidRefreshTokenException.class,
                () -> authService.refresh("expired-token")
        );

        verify(jwtService, never()).generateToken(any());
        verify(refreshTokenRepository, never()).save(any());
    }

    // 9. Blank refresh token rejection

    @Test
    void testBlankRefreshTokenIsRejected() {

        assertThrows(
                InvalidRefreshTokenException.class,
                () -> authService.refresh(" ")
        );

        verifyNoInteractions(refreshTokenRepository);
        verifyNoInteractions(jwtService);
    }

    // 10. Logout revokes refresh token

    @Test
    void testLogoutRevokesRefreshToken() {

        RefreshToken token = RefreshToken.builder()
                .user(User.builder()
                        .email("new@example.com")
                        .status("ACTIVE")
                        .build())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.of(token));

        authService.logout("refresh-token");

        assertNotNull(token.getRevokedAt());

        verify(refreshTokenRepository)
                .findByTokenHashForUpdate(anyString());

        verify(refreshTokenRepository).save(token);
        verifyNoInteractions(jwtService);
    }
}
