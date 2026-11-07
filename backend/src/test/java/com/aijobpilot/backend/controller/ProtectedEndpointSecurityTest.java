package com.aijobpilot.backend.controller;

import com.aijobpilot.backend.security.JwtService;
import com.aijobpilot.backend.service.JobApplicationService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobApplicationController.class)
@AutoConfigureMockMvc
@Import({com.aijobpilot.backend.config.SecurityConfig.class, com.aijobpilot.backend.security.JwtAuthenticationFilter.class})
class ProtectedEndpointSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobApplicationService jobApplicationService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @Test
    void rejectsUnauthenticatedProtectedRequest() throws Exception {
        mockMvc.perform(get("/api/job-applications"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void allowsProtectedRequestWithValidJwt() throws Exception {
        UserDetails user = new User("user@example.com", "password", Collections.emptyList());
        when(jwtService.extractUsername("valid-token")).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(user);
        when(jwtService.isTokenValid("valid-token", user)).thenReturn(true);
        when(jobApplicationService.findAll("user@example.com")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/job-applications").header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(jobApplicationService).findAll("user@example.com");
    }
}
