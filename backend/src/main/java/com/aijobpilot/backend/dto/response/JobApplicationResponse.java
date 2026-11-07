package com.aijobpilot.backend.dto.response;

import com.aijobpilot.backend.entity.JobApplication;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class JobApplicationResponse {
    Long id;
    String companyName;
    String jobTitle;
    String jobDescription;
    String status;
    LocalDate appliedDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static JobApplicationResponse from(JobApplication application) {
        return JobApplicationResponse.builder()
                .id(application.getId())
                .companyName(application.getCompanyName())
                .jobTitle(application.getJobTitle())
                .jobDescription(application.getJobDescription())
                .status(application.getStatus())
                .appliedDate(application.getAppliedDate())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }
}
