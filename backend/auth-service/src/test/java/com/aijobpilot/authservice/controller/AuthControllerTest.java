
package com.aijobpilot.authservice.controller;

import com.aijobpilot.authservice.dto.request.RefreshTokenRequest;
import com.aijobpilot.authservice.dto.request.SignupRequest;
import com.aijobpilot.authservice.dto.response.AuthResponse;
import com.aijobpilot.authservice.exception.DuplicateEmailException;
import com.aijobpilot.authservice.exception.GlobalExceptionHandler;
import com.aijobpilot.authservice.security.JwtAuthenticationFilter;
import com.aijobpilot.authservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void refreshSuccess() throws Exception {
        AuthResponse response = AuthResponse.builder().token("access").refreshToken("next").build();
        when(authService.refresh("refresh")).thenReturn(response);

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\":\"refresh\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("access"))
                .andExpect(jsonPath("$.refreshToken").value("next"));
    }

    @Test
    void logoutRevokesToken() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\":\"refresh\"}"))
                .andExpect(status().isNoContent());
        verify(authService).logout("refresh");
    }

    @Test
    void signupSuccess() throws Exception {

        AuthResponse response = AuthResponse.builder()
                .token("mockJwtToken")
                .email("new@example.com")
                .role("USER")
                .message("User registered successfully")
                .build();

        when(authService.signup(any(SignupRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "new@example.com",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.token").value("mockJwtToken"))
                .andExpect(jsonPath("$.role").value("USER"));

        verify(authService).signup(any(SignupRequest.class));
    }

    @Test
    void signupWithInvalidEmailReturnsBadRequest() throws Exception {

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "invalid-email",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.email").exists());

        verifyNoInteractions(authService);
    }

    @Test
    void signupWithEmptyEmailReturnsBadRequest() throws Exception {

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.email").exists());

        verifyNoInteractions(authService);
    }

    @Test
    void signupWithShortPasswordReturnsBadRequest() throws Exception {

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "new@example.com",
                                    "password": "123"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.password").exists());

        verifyNoInteractions(authService);
    }

    @Test
    void signupWithEmptyPasswordReturnsBadRequest() throws Exception {

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "new@example.com",
                                    "password": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.password").exists());

        verifyNoInteractions(authService);
    }

    @Test
    void signupWithDuplicateEmailReturnsConflict() throws Exception {

        when(authService.signup(any(SignupRequest.class)))
                .thenThrow(new DuplicateEmailException("Email is already in use"));

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "existing@example.com",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Email is already in use"));

        verify(authService).signup(any(SignupRequest.class));
    }
}
