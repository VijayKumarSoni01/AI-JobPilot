package com.aijobpilot.backend.service;

import com.aijobpilot.backend.dto.request.JobApplicationRequest;
import com.aijobpilot.backend.entity.JobApplication;
import com.aijobpilot.backend.entity.User;
import com.aijobpilot.backend.exception.ResourceNotFoundException;
import com.aijobpilot.backend.repository.JobApplicationRepository;
import com.aijobpilot.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {
    @Mock JobApplicationRepository applicationRepository;
    @Mock UserRepository userRepository;
    @InjectMocks JobApplicationService service;
    private User owner;

    @BeforeEach
    void setUp() {
        owner = User.builder().id(1L).email("owner@example.com").build();
    }

    @Test
    void cannotReadAnotherUsersApplication() {
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(applicationRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById("owner@example.com", 99L));
    }

    @Test
    void cannotUpdateOrDeleteAnotherUsersApplication() {
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(applicationRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());
        JobApplicationRequest request = new JobApplicationRequest();
        request.setCompanyName("Acme");
        request.setJobTitle("Engineer");
        request.setStatus("APPLIED");

        assertThrows(ResourceNotFoundException.class, () -> service.update("owner@example.com", 99L, request));
        assertThrows(ResourceNotFoundException.class, () -> service.delete("owner@example.com", 99L));
        verify(applicationRepository, never()).save(any());
        verify(applicationRepository, never()).delete(any());
    }
}
