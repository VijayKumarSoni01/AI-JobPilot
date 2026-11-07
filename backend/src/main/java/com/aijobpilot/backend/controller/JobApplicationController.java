package com.aijobpilot.backend.controller;

import com.aijobpilot.backend.dto.request.JobApplicationRequest;
import com.aijobpilot.backend.dto.response.JobApplicationResponse;
import com.aijobpilot.backend.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService service;

    @GetMapping
    public List<JobApplicationResponse> findAll(Authentication authentication) {
        return service.findAll(authentication.getName());
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> create(Authentication authentication,
                                                          @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(authentication.getName(), request));
    }

    @GetMapping("/{id}")
    public JobApplicationResponse findById(Authentication authentication, @PathVariable Long id) {
        return service.findById(authentication.getName(), id);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse update(Authentication authentication, @PathVariable Long id,
                                         @Valid @RequestBody JobApplicationRequest request) {
        return service.update(authentication.getName(), id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication authentication, @PathVariable Long id) {
        service.delete(authentication.getName(), id);
    }
}
