package com.aijobpilot.backend.service;

import com.aijobpilot.backend.dto.request.JobApplicationRequest;
import com.aijobpilot.backend.dto.response.JobApplicationResponse;
import com.aijobpilot.backend.entity.JobApplication;
import com.aijobpilot.backend.entity.User;
import com.aijobpilot.backend.exception.ResourceNotFoundException;
import com.aijobpilot.backend.repository.JobApplicationRepository;
import com.aijobpilot.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class JobApplicationService {
    private static final Set<String> ALLOWED_STATUSES = Set.of("SAVED", "APPLIED", "INTERVIEW", "OFFER", "REJECTED", "WITHDRAWN");

    private final JobApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<JobApplicationResponse> findAll(String email) {
        Long userId = findUser(email).getId();
        return applicationRepository.findAllByUserIdOrderByUpdatedAtDesc(userId).stream()
                .map(JobApplicationResponse::from).toList();
    }

    public JobApplicationResponse create(String email, JobApplicationRequest request) {
        JobApplication application = new JobApplication();
        application.setUser(findUser(email));
        apply(application, request);
        return JobApplicationResponse.from(applicationRepository.save(application));
    }

    @Transactional(readOnly = true)
    public JobApplicationResponse findById(String email, Long id) {
        return JobApplicationResponse.from(findOwned(email, id));
    }

    public JobApplicationResponse update(String email, Long id, JobApplicationRequest request) {
        JobApplication application = findOwned(email, id);
        apply(application, request);
        return JobApplicationResponse.from(applicationRepository.save(application));
    }

    public void delete(String email, Long id) {
        applicationRepository.delete(findOwned(email, id));
    }

    private JobApplication findOwned(String email, Long id) {
        Long userId = findUser(email).getId();
        return applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void apply(JobApplication application, JobApplicationRequest request) {
        String status = request.getStatus().toUpperCase();
        if (!ALLOWED_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid application status");
        }
        application.setCompanyName(request.getCompanyName().trim());
        application.setJobTitle(request.getJobTitle().trim());
        application.setJobDescription(request.getJobDescription());
        application.setStatus(status);
        application.setAppliedDate(request.getAppliedDate());
    }
}
